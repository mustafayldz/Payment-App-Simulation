package com.example.paymentappsimulation.data.remote

import com.example.paymentappsimulation.data.model.PaymentRequestModel
import com.example.paymentappsimulation.data.model.PaymentResponseModel


interface ApiService {
    suspend fun makePayment(request: PaymentRequestModel): PaymentResponseModel
}
