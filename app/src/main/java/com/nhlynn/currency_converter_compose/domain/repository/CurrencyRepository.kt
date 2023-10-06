package com.nhlynn.currency_converter_compose.domain.repository

import com.nhlynn.currency_converter_compose.domain.model.CurrencyRate
import com.nhlynn.currency_converter_compose.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencyRatesList(): Resource<List<CurrencyRate>>
}