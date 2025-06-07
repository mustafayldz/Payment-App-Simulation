package com.example.paymentappsimulation.data.respository

import com.example.paymentappsimulation.data.remote.ApiService
import com.example.paymentappsimulation.data.model.PaymentRequestModel
import com.example.paymentappsimulation.data.model.PaymentResponseModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class SaleReposityRemote @Inject constructor() : ApiService {
    override suspend fun makePayment(request: PaymentRequestModel): PaymentResponseModel {
        delay(2000) // simulate network delay

        return if (request.amount < 1000.0) {
            PaymentResponseModel(
                status = "approved",
                transactionId = System.currentTimeMillis().toString(),
                message = "Approved"
            )
        } else {
            PaymentResponseModel(
                status = "declined",
                transactionId = System.currentTimeMillis().toString(),
                message = "Insufficient funds"
            )
        }
    }
}
