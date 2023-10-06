package com.nhlynn.currency_converter_compose.network

import com.nhlynn.currency_converter_compose.network.response.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String = API_KEY
    ): CurrencyResponse

    companion object {
        const val API_KEY = "fca_live_yL448454VbxSJvCyuMLFWL4neA07bYebRI98AFMb"
        const val BASE_URL = "https://api.freecurrencyapi.com/"
    }
}