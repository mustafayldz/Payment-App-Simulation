package com.example.paymentappsimulation.presentation.dashboard.refund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.respository.SaleReposityLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoidViewModel @Inject constructor(
    private val repository: SaleReposityLocal
) : ViewModel() {

    private val _sales = MutableStateFlow<List<PaymentEntity>>(emptyList())
    val sales = _sales.asStateFlow()

    fun searchSalesByTxId(txId: String) {

        println("----------- Searching for sales with transaction ID: $txId -----------")

        viewModelScope.launch {
            repository.getSaleByTransactionId(txId).collect {
                _sales.value = it
            }
        }
    }

    fun voidSale(sale: PaymentEntity) {
        viewModelScope.launch {

            val refundedSale = sale.copy(
                id = 0,
                status = "void",
                timestamp = System.currentTimeMillis().toString(),
                transactionId = "V-${sale.transactionId}"
            )

            println("----------- Processing refund for sale: $refundedSale -----------")

            repository.insertSale(refundedSale)
        }
    }

    fun clearSales() {
        viewModelScope.launch {
            _sales.value = emptyList()
        }
    }
}
