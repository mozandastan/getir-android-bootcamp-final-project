package com.getir.patika.shoppingapp.di

import com.getir.patika.shoppingapp.data.repository.ProductRepository
import com.getir.patika.shoppingapp.data.service.ApiService
import com.getir.patika.shoppingapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepository(apiService)
    }
}