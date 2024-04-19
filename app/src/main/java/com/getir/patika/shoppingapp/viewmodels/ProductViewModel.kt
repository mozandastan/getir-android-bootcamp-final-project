package com.getir.patika.shoppingapp.viewmodels

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

    private val _horizontalProductList = MutableLiveData<List<Product>>()
    val horizontalProductList: LiveData<List<Product>> = _horizontalProductList

    private val _verticalProductList = MutableLiveData<List<Product>>()
    val verticalProductList: LiveData<List<Product>> = _verticalProductList

    init {
        getProducts()
        getSuggestedProducts()
    }
    private fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    is CallResult.Loading -> {
                    }
                    is CallResult.Success -> {
                        result.data.let {
                            _verticalProductList.value = it
                        }
                    }
                    is CallResult.Error -> {
                    }
                }
            }
        }
    }

    private fun getSuggestedProducts() {
        viewModelScope.launch {
            productRepository.getSuggestedProducts().collect { result ->
                when (result) {
                    is CallResult.Loading -> {
                    }
                    is CallResult.Success -> {
                        result.data.let {
                            _horizontalProductList.value = it
                        }
                    }
                    is CallResult.Error -> {
                    }
                }
            }
        }
    }
}