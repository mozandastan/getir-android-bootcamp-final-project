package com.getir.patika.shoppingapp.data.service

import com.getir.patika.shoppingapp.data.models.ProductsItem
import com.getir.patika.shoppingapp.data.models.SuggestedProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductsItem>>
    @GET("suggestedProducts")
    suspend fun getSuggestedProducts(): Response<List<SuggestedProductItem>>
}