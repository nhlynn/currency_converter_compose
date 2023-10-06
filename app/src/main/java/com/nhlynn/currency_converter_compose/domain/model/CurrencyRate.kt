package com.nhlynn.currency_converter_compose.domain.model

data class CurrencyRate(
    val code:String,
    val name:String,
    val rate:Double
)
