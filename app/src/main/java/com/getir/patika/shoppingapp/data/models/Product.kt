package com.getir.patika.shoppingapp.data.models

data class Product(
    val id: String, //common
    val name: String, //common
    val price: Double, //common
    val priceText: String?, //common
    val imageURL: String?,
    val shortDescription: String?,
    val squareThumbnailURL: String?,
    val attribute: String?,
    val thumbnailURL: String?,
    val category: String?,
    val status: Int?,
    val unitPrice: Double?
)