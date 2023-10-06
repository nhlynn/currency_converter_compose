package com.nhlynn.currency_converter_compose.presentation.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhlynn.currency_converter_compose.domain.model.Resource
import com.nhlynn.currency_converter_compose.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    var state by mutableStateOf(MainScreenState())

    init {
        getCurrencyRateList()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.ButtonSheetItemClicked -> {
                if (state.selection == SelectionState.FROM) {
                    state = state.copy(
                        fromCurrencyCode = event.currencyCode,
                        fromCurrencyName = event.currencyName
                    )
                } else if (state.selection == SelectionState.TO) {
                    state = state.copy(
                        toCurrencyCode = event.currencyCode,
                        toCurrencyName = event.currencyName
                    )
                }
                updateCurrencyValue()
            }

            MainScreenEvent.FromCurrencySelect -> {
                state = state.copy(selection = SelectionState.FROM)
            }

            is MainScreenEvent.NumberButtonClicked -> {
                updateCurrencyValue(value = event.value)
            }

            MainScreenEvent.SwapIconClicked -> {
                state = state.copy(
                    fromCurrencyCode = state.toCurrencyCode,
                    fromCurrencyName = state.toCurrencyName,
                    fromCurrencyValue = state.toCurrencyValue,
                    toCurrencyCode = state.fromCurrencyCode,
                    toCurrencyName = state.fromCurrencyName,
                    toCurrencyValue = state.fromCurrencyValue
                )
            }

            MainScreenEvent.ToCurrencySelect -> {
                state = state.copy(selection = SelectionState.TO)
            }
        }
    }

    private fun getCurrencyRateList() {
        viewModelScope.launch {
            state = when (val response = repository.getCurrencyRatesList()) {
                is Resource.Error -> {
                    state.copy(
                        currencyRates = emptyMap(),
                        error = response.message
                    )
                }

                is Resource.Success -> {
                    state.copy(
                        currencyRates = response.data?.associateBy { it.code } ?: emptyMap(),
                        error = null
                    )
                }
            }
        }
    }

    private fun updateCurrencyValue(value: String? = null) {
        val currentCurrencyValue = when (state.selection) {
            SelectionState.FROM -> state.fromCurrencyValue
            SelectionState.TO -> state.toCurrencyValue
        }

        val fromCurrencyRate = state.currencyRates[state.fromCurrencyCode]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRates[state.toCurrencyCode]?.rate ?: 0.0

        val updateCurrencyValue = when (value) {
            null -> currentCurrencyValue
            "C" -> "0.00"
            else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
        }

        val numberFormat = DecimalFormat("#.00")
        when (state.selection) {
            SelectionState.FROM -> {
                val fromValue = updateCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updateCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }

            SelectionState.TO -> {
                val toValue = updateCurrencyValue.toDoubleOrNull() ?: 0.0
                val fromValue = toValue / toCurrencyRate * fromCurrencyRate
                state = state.copy(
                    toCurrencyValue = updateCurrencyValue,
                    fromCurrencyValue = numberFormat.format(fromValue)
                )
            }
        }
    }
}