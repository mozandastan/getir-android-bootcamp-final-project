package com.getir.patika.shoppingapp.data.service

sealed class CallResult<out T> {
    data object Loading : CallResult<Nothing>()
    data class Success<out T>(val data: T) : CallResult<T>()
    data class Error(val message: String) : CallResult<Nothing>()
}