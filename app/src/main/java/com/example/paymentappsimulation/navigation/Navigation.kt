package com.example.paymentappsimulation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.paymentappsimulation.presentation.dashboard.DashboardScreen
import com.example.paymentappsimulation.presentation.dashboard.history.HistoryScreen
import com.example.paymentappsimulation.presentation.dashboard.refund.RefundScreen
import com.example.paymentappsimulation.presentation.dashboard.refund.VoidScreen
import com.example.paymentappsimulation.presentation.login.LoginScreen
import com.example.paymentappsimulation.presentation.dashboard.sale.SaleScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object NewSale : Screen("new_sale")
    object Refund : Screen("refund")
    object Void : Screen("void")
    object History : Screen("history")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        composable(Screen.NewSale.route) {
            SaleScreen(navController = navController)
        }

        composable(Screen.Refund.route) {
            RefundScreen(navController = navController)
        }

        composable(Screen.Void.route) {
            VoidScreen(navController = navController)
        }

        composable(Screen.History.route) {
            HistoryScreen(navController = navController)
        }
    }
}
