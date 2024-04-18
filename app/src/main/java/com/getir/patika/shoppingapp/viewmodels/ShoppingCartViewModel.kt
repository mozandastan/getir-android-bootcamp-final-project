package com.getir.patika.shoppingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getir.patika.shoppingapp.data.models.Product

class ShoppingCartViewModel : ViewModel() {

    private val _cartProductList = MutableLiveData<List<Product>>()
    val cartProductList: LiveData<List<Product>> = _cartProductList

    init {
        val cartItemList = mutableListOf<Product>()
        for (i in 1..4) {
            cartItemList.add(
                Product(
                    "Attribute $i",
                    "ID $i",
                    "https://images.crunchbase.com/image/upload/c_pad,f_auto,q_auto:eco,dpr_1/zm60fzffzzatwv35syvs",
                    "Product $i",
                    10.0 * i,
                    "$10.00",
                    "Short description for Product $i",
                    "https://example.com/thumbnail$i"
                )
            )
        }
        _cartProductList.value = cartItemList
    }
}