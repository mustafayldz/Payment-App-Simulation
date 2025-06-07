package com.example.paymentappsimulation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.paymentappsimulation.navigation.NavGraph
import com.example.paymentappsimulation.presentation.login.LoginViewModel
import com.example.paymentappsimulation.ui.theme.PaymentAppSimulationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PaymentAppSimulationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(
                        navController = navController,
                        startDestination = viewModel.initialRoute(this)
                    )
                }
            }
        }
    }
}