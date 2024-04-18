package com.getir.patika.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getir.patika.shoppingapp.data.models.Product

class ProductViewModel : ViewModel() {

    private val _horizontalProductList = MutableLiveData<List<Product>>()
    val horizontalProductList: LiveData<List<Product>> = _horizontalProductList

    private val _verticalProductList = MutableLiveData<List<Product>>()
    val verticalProductList: LiveData<List<Product>> = _verticalProductList

    init {
        val horizontalList = mutableListOf<Product>()
        for (i in 1..7) {
            horizontalList.add(
                Product(
                    "Attribute $i",
                    "ID $i",
                    "https://example.com/image$i",
                    "Product $i",
                    10.0 * i,
                    "$10.00",
                    "Short description for Product $i",
                    "https://example.com/thumbnail$i"
                )
            )
        }
        _horizontalProductList.value = horizontalList

        val verticalList = mutableListOf<Product>()
        for (i in 7..20) {
            verticalList.add(
                Product(
                    "Attribute $i",
                    "ID $i",
                    "https://example.com/image$i",
                    "Product $i",
                    10.0 * i,
                    "$10.00",
                    "Short description for Product $i",
                    "https://example.com/thumbnail$i"
                )
            )
        }
        _verticalProductList.value = verticalList
    }
}