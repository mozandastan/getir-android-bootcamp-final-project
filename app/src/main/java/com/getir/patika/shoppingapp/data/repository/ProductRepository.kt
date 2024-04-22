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
    //API call for Products
    suspend fun getProducts(): Flow<CallResult<List<Product>>> {
        //Get List from response if the List is not null
        return fetchProductsFromApi { apiService.getProducts().body()?.get(0)?.products ?: emptyList() }
    }
    //API call for SuggestedProducts
    suspend fun getSuggestedProducts(): Flow<CallResult<List<Product>>> {
        //Get List from response if the List is not null
        return fetchProductsFromApi { apiService.getSuggestedProducts().body()?.get(0)?.products ?: emptyList() }
    }
    private suspend fun fetchProductsFromApi(apiCall: suspend () -> List<Product>):
        Flow<CallResult<List<Product>>> = flow {
        //Loading Animation could be displayed in UI according to state
        emit(CallResult.Loading)
        try {
            //Make the API call and get the response
            val response = apiCall.invoke()
            //If data was received successfully, success status
            emit(CallResult.Success(response))
        } catch (e: Exception) {
            emit(CallResult.Error(e.message ?: "An error occurred while retrieving data"))
        }
    }

}