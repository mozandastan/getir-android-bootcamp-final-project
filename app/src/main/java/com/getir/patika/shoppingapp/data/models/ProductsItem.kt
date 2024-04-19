package com.getir.patika.shoppingapp.data.models

data class ProductsItem(
    val email: String,
    val id: String,
    val name: String,
    val password: String,
    val productCount: Int,
    val products: List<Product>
)