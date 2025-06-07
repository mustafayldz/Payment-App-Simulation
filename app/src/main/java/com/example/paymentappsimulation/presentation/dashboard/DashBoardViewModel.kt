package com.example.paymentappsimulation.presentation.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.paymentappsimulation.data.util.PreferenceManager
import com.example.paymentappsimulation.navigation.Screen

class DashBoardViewModel : ViewModel() {

    fun logout(context: Context, navController: NavController) {
        PreferenceManager.clearUsername(context)
        println("User logged out successfully.")

        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Dashboard.route) { inclusive = true }
        }
    }
}
