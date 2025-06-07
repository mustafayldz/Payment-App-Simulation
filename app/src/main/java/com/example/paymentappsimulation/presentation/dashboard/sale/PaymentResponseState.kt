package com.example.paymentappsimulation.presentation.dashboard.sale

import com.example.paymentappsimulation.data.model.PaymentResponseModel

sealed class PaymentResponseState {
    object Idle : PaymentResponseState()
    object Loading : PaymentResponseState()
    data class Result(val response: PaymentResponseModel) : PaymentResponseState()
}
