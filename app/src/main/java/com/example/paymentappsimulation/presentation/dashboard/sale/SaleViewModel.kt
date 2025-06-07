package com.example.paymentappsimulation.presentation.dashboard.sale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.model.PaymentRequestModel
import com.example.paymentappsimulation.data.respository.SaleReposityLocal
import com.example.paymentappsimulation.data.respository.SaleReposityRemote
import com.example.paymentappsimulation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val apiService: SaleReposityRemote,
    private val repository: SaleReposityLocal
) : ViewModel() {

    private val _paymentState = MutableStateFlow<PaymentResponseState>(PaymentResponseState.Idle)
    val paymentState: StateFlow<PaymentResponseState> = _paymentState

    fun makePayment(amount: Double, tip: Double, cardType: String) {
        _paymentState.value = PaymentResponseState.Loading

        viewModelScope.launch {
            val response = apiService.makePayment(
                PaymentRequestModel(amount = amount, tip = tip, cardType = cardType)
            )



            delay(1000)




            if (response.status == "approved") {
                val entity = PaymentEntity(
                    id = 0,
                    amount = amount + tip,
                    cardType = cardType,
                    timestamp = System.currentTimeMillis().toString(),
                    status = response.status,
                    transactionId = UUID.randomUUID().toString()
                )
                repository.insertSale(entity)
            }




            _paymentState.value = PaymentResponseState.Result(response)
        }
    }

    fun resetState() {
        _paymentState.value = PaymentResponseState.Idle
    }

    fun cancelPayment(navController: NavController) {
        navController.navigate(Screen.Dashboard.route) {
            popUpTo(Screen.NewSale.route) { inclusive = true }
        }




    }
}
