package com.nhlynn.currency_converter_compose.presentation.main_screen

import com.nhlynn.currency_converter_compose.domain.model.CurrencyRate

data class MainScreenState(
    val fromCurrencyCode: String = "INR",
    val fromCurrencyName: String = "Indian Rupee",
    val toCurrencyCode: String = "USD",
    val toCurrencyName: String = "US Dollar",
    val fromCurrencyValue: String = "0.00",
    val toCurrencyValue: String = "0.00",
    val selection: SelectionState = SelectionState.FROM,
    val currencyRates: Map<String, CurrencyRate> = emptyMap(),
    var error: String? = null
)

enum class SelectionState{
    FROM,
    TO
}
