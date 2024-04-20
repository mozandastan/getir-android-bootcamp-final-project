package com.getir.patika.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getir.patika.shoppingapp.data.models.Product

class CartViewModel : ViewModel() {

    private val _cartProductList = MutableLiveData<List<Product>>()
    val cartProductList: LiveData<List<Product>> = _cartProductList

    init {
        val cartItemList = mutableListOf<Product>()
        for (i in 1..7) {
        }
        _cartProductList.value = cartItemList
    }
}