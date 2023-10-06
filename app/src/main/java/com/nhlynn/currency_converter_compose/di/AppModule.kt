package com.nhlynn.currency_converter_compose.di

import com.nhlynn.currency_converter_compose.network.ApiService
import com.nhlynn.currency_converter_compose.network.ApiService.Companion.BASE_URL
import com.nhlynn.currency_converter_compose.network.repository.CurrencyRepositoryImpl
import com.nhlynn.currency_converter_compose.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): ApiService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: ApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            api = api
        )
    }
}