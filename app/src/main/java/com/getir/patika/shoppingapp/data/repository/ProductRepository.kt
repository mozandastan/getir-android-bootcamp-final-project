package com.getir.patika.shoppingapp.data.repository

import com.getir.patika.shoppingapp.data.models.Product
import com.getir.patika.shoppingapp.data.service.ApiService
import com.getir.patika.shoppingapp.data.service.CallResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts(): Flow<CallResult<List<Product>>> = flow {
        emit(CallResult.Loading)
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                val productsResponse = response.body()
                val products = productsResponse?.get(0)?.products
                emit(CallResult.Success(products ?: emptyList()))
            } else {
                emit(CallResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(CallResult.Error(e.message ?: "An error occurred"))
        }
    }

    suspend fun getSuggestedProducts(): Flow<CallResult<List<Product>>> = flow {
        emit(CallResult.Loading)
        try {
            val response = apiService.getSuggestedProducts()
            if (response.isSuccessful) {
                val suggestedProductsResponse = response.body()
                val products = suggestedProductsResponse?.get(0)?.products
                emit(CallResult.Success(products ?: emptyList()))
            } else {
                emit(CallResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(CallResult.Error(e.message ?: "An error occurred"))
        }
    }
}