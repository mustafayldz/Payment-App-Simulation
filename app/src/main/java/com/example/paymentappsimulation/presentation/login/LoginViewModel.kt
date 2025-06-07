package com.example.paymentappsimulation.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.paymentappsimulation.data.util.PreferenceManager
import com.example.paymentappsimulation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun initialRoute(context: Context): String {
        val savedUsername = PreferenceManager.getUsername(context)
        return if (savedUsername?.isNotEmpty() == true) {
            Screen.Dashboard.route
        } else {
            Screen.Login.route
        }
    }

    fun login(context: Context, username: String, password: String): Boolean {
        val isValid = username == "rapidcent" && password == "qwe123"

        if (isValid) {
            PreferenceManager.saveUsername(context, username)
            _isLoggedIn.value = true
        }

        println("------------- login is valid: $isValid")
        return isValid
    }
}
