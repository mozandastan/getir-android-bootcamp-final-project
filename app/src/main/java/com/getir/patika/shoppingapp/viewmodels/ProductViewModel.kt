package com.getir.patika.shoppingapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.data.repository.ProductRepository
import com.getir.patika.shoppingapp.data.service.CallResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    //List for the suggested products
    private val _horizontalProductList = MutableLiveData<List<Product>>()
    val horizontalProductList: LiveData<List<Product>> = _horizontalProductList
    //List for the products
    private val _verticalProductList = MutableLiveData<List<Product>>()
    val verticalProductList: LiveData<List<Product>> = _verticalProductList
    //Item for the Detail Screen
    private val _selectedProduct  = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    init {
        getProducts()
        getSuggestedProducts()
    }
    private fun getProducts() {
        //Get the datas from repository
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    //is CallResult.Loading -> //Loading could be displayed in UI
                    is CallResult.Success -> {
                        result.data.let { products ->
                            _verticalProductList.value = products
                        }
                    }
                    else -> Log.e("ProductViewModel", "Failed to fetch vertical products")
                }
            }
        }
    }
    private fun getSuggestedProducts() {
        //Get the datas from repository
        viewModelScope.launch {
            productRepository.getSuggestedProducts().collect { result ->
                when (result) {
                    //is CallResult.Loading -> //Loading could be displayed in UI
                    is CallResult.Success -> {
                        result.data.let { products ->
                            _horizontalProductList.value = products
                        }
                    }
                    else -> Log.e("ProductViewModel", "Failed to fetch horizontal products")
                }
            }
        }
    }
    fun setSelectedProduct(product: Product) {
        //Change the selected item
        _selectedProduct.value = product
    }
}