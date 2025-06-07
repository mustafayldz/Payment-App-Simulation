package com.example.paymentappsimulation.data.respository


import com.example.paymentappsimulation.data.local.SaleDao
import com.example.paymentappsimulation.data.model.PaymentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaleReposityLocal @Inject constructor(
    private val saleDao: SaleDao
) {
    suspend fun insertSale(sale: PaymentEntity) = saleDao.insertSale(sale)

    fun getAllSales(): Flow<List<PaymentEntity>> = saleDao.getAllSales()

    fun getSaleByTransactionId(txId: String): Flow<List<PaymentEntity>> = saleDao.getSalesByTransactionId(txId)

    suspend fun clearSales() = saleDao.clearSales()
}