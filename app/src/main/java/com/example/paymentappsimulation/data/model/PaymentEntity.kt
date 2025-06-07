package com.example.paymentappsimulation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val cardType: String,
    val timestamp: String,
    val status: String,
    val transactionId: String
)
