package com.nhlynn.currency_converter_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhlynn.currency_converter_compose.presentation.main_screen.MainScreen
import com.nhlynn.currency_converter_compose.presentation.main_screen.MainScreenViewModel
import com.nhlynn.currency_converter_compose.presentation.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                Surface {
                    MainScreen(
                        state = viewModel.state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}