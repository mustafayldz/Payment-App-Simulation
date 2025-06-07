package com.example.paymentappsimulation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paymentappsimulation.data.model.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {

    @Insert
    suspend fun insertSale(sale: PaymentEntity)

    @Query("SELECT * FROM sales ORDER BY timestamp DESC")
    fun getAllSales(): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM sales WHERE transactionId LIKE '%' || :txId || '%'")
    fun getSalesByTransactionId(txId: String): kotlinx.coroutines.flow.Flow<List<PaymentEntity>>

    @Query("DELETE FROM sales")
    suspend fun clearSales()
}
