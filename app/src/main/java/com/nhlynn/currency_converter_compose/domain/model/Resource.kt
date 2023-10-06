package com.nhlynn.currency_converter_compose.domain.model

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(message = message)
}
