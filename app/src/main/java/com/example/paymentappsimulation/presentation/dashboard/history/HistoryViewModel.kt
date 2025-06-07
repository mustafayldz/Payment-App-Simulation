package com.example.paymentappsimulation.presentation.dashboard.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.respository.SaleReposityLocal
import com.example.paymentappsimulation.data.util.timePeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: SaleReposityLocal
) : ViewModel() {

    private val _allSales = MutableStateFlow<List<PaymentEntity>>(emptyList())
    private val _sales = MutableStateFlow<List<PaymentEntity>>(emptyList())
    val sales = _sales.asStateFlow()

    init {
        fetchAllSales()
    }

    private fun fetchAllSales() {
        viewModelScope.launch {
            repository.getAllSales().collect {
                _allSales.value = it
                _sales.value = it // initial full load
            }
        }
    }

    fun filterSalesByTime(filter: timePeriod) {
        viewModelScope.launch {
            val allSales = _allSales.value
            val now = System.currentTimeMillis()

            val filtered = when (filter) {
                timePeriod.Today -> allSales.filter { it.timestamp >= now.startOfDayMillis().toString() }
                timePeriod.Week -> allSales.filter { it.timestamp >= now.startOfWeekMillis().toString() }
                timePeriod.Month -> allSales.filter { it.timestamp >= now.startOfMonthMillis().toString() }
                timePeriod.Year -> allSales.filter { it.timestamp >= now.startOfYearMillis().toString() }
                timePeriod.All -> allSales
            }

            _sales.value = filtered
        }
    }

    // Date helpers
    private fun Long.startOf(unit: Int, value: Int? = null): Long {
        return Calendar.getInstance().apply {
            timeInMillis = this@startOf
            value?.let { set(unit, it) }
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun Long.startOfDayMillis(): Long = this.startOf(Calendar.DAY_OF_MONTH)
    private fun Long.startOfWeekMillis(): Long = this.startOf(Calendar.DAY_OF_WEEK, Calendar.getInstance().firstDayOfWeek)
    private fun Long.startOfMonthMillis(): Long = this.startOf(Calendar.DAY_OF_MONTH, 1)
    private fun Long.startOfYearMillis(): Long = this
        .startOf(Calendar.MONTH, Calendar.JANUARY)
        .startOf(Calendar.DAY_OF_MONTH, 1)
}

