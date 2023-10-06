package com.nhlynn.currency_converter_compose.presentation.main_screen

sealed class MainScreenEvent {
    object FromCurrencySelect : MainScreenEvent()
    object ToCurrencySelect : MainScreenEvent()
    object SwapIconClicked : MainScreenEvent()
    data class ButtonSheetItemClicked(val currencyCode: String,val currencyName: String) : MainScreenEvent()
    data class NumberButtonClicked(val value: String) : MainScreenEvent()
}
