package com.getir.patika.shoppingapp.data.models

data class SuggestedProductItem(
    val id: String,
    val name: String,
    val products: List<Product>
)