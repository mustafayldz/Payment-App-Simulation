package com.example.paymentappsimulation.data.model

data class PaymentRequestModel(
    val amount: Double,
    val tip: Double,
    val cardType: String
)