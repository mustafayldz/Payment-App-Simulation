package com.example.paymentappsimulation.data.model

data class PaymentResponseModel(
    val status: String,          // "approved" or "declined"
    val transactionId: String,
    val message: String
)