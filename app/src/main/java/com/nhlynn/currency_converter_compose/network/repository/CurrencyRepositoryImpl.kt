package com.nhlynn.currency_converter_compose.network.repository

import com.nhlynn.currency_converter_compose.network.ApiService
import com.nhlynn.currency_converter_compose.network.response.toCurrencyRates
import com.nhlynn.currency_converter_compose.domain.model.CurrencyRate
import com.nhlynn.currency_converter_compose.domain.model.Resource
import com.nhlynn.currency_converter_compose.domain.repository.CurrencyRepository
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: ApiService
) : CurrencyRepository {
    override suspend fun getCurrencyRatesList(): Resource<List<CurrencyRate>> {
        return try {
            val response = getRemoteCurrencyRates()
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(message = "Couldn't reach server, check your internet connection")
        } catch (e: Exception) {
            Resource.Error(message = "Oops, something went wrong!")
        }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }
}