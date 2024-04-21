package com.getir.patika.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getir.patika.shoppingapp.data.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<Product>>()
    val cartItems: LiveData<List<Product>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    init {
        _cartItems.value = mutableListOf()
        _totalPrice.value = 0.0
    }

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.add(product)
        _cartItems.value = currentItems
        increaseTotalPrice(product.price)
    }
    fun removeFromCart(product: Product) {
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.remove(product)
        _cartItems.value = currentItems
        decreaseTotalPrice(product.price)
    }
    fun clearCart() {
        _cartItems.value = emptyList()
        _totalPrice.value = 0.0
    }
    fun isProductInCart(product: Product): Boolean {
        return _cartItems.value?.any { it.id == product.id } ?: false
    }
    fun getProductCount(product: Product): Int {
        return _cartItems.value?.count { it.id == product.id } ?: 0
    }
    private fun increaseTotalPrice(amount: Double) {
        _totalPrice.value = _totalPrice.value?.plus(amount) ?: amount
    }
    private fun decreaseTotalPrice(amount: Double) {
        _totalPrice.value = _totalPrice.value?.minus(amount) ?: 0.0
    }
}