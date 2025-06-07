package com.example.paymentappsimulation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paymentappsimulation.data.local.SaleDao
import com.example.paymentappsimulation.data.model.PaymentEntity

@Database(
    entities = [PaymentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PaymentDatabase : RoomDatabase() {
    abstract fun saleDao(): SaleDao
}
