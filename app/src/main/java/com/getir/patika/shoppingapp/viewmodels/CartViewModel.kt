package com.getir.patika.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getir.patika.shoppingapp.data.models.Product

class CartViewModel : ViewModel() {

    //List of products in cart
    private val _cartItems = MutableLiveData<List<Product>>(emptyList())
    val cartItems: LiveData<List<Product>> = _cartItems
    //Value of total cart price
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    fun addToCart(product: Product) {
        //Add product to the cart list
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.add(product)
        _cartItems.value = currentItems
        updateTotalPrice(product.price)
    }
    fun removeFromCart(product: Product) {
        //Remove product from the cart list
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.remove(product)
        _cartItems.value = currentItems
        updateTotalPrice(-product.price)
    }
    private fun updateTotalPrice(amount: Double) {
        //Update total cart price
        _totalPrice.value = (_totalPrice.value ?: 0.0) + amount
    }
    fun clearCart() {
        //Clear all items in the cart
        _cartItems.value = emptyList()
        _totalPrice.value = 0.0
    }
    fun isProductInCart(product: Product): Boolean {
        //Check if product already in the cart
        return _cartItems.value?.any { it.id == product.id } ?: false
    }
    fun getProductCount(product: Product): Int {
        //Check that product count
        return _cartItems.value?.count { it.id == product.id } ?: 0
    }
}