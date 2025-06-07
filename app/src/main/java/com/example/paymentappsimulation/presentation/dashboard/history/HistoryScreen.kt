package com.example.paymentappsimulation.presentation.dashboard.history

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.data.util.timePeriod
import com.example.paymentappsimulation.ui.theme.GreenDark
import com.example.paymentappsimulation.ui.theme.GreyDark
import com.example.paymentappsimulation.ui.theme.RedDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController ,viewModel: HistoryViewModel = hiltViewModel()) {
    val sales by viewModel.sales.collectAsState()
    var selectedFilter by remember { mutableStateOf(timePeriod.All) }

    val filterOptions = listOf(
        timePeriod.All to "All",
        timePeriod.Today to "Today",
        timePeriod.Week to "Week",
        timePeriod.Month to "Month",
        timePeriod.Year to "Year"
    )

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Transaction History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )  {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterOptions.forEach { (filter, label) ->
                    Button(
                        onClick = {
                            selectedFilter = filter
                            viewModel.filterSalesByTime(filter)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedFilter == filter) GreenDark else GreyDark
                        )
                    ) {
                        Text(label, fontSize = 12.sp)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                items(sales) { sale ->
                    SaleItem(sale)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SaleItem(sale: PaymentEntity) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("ID: ${sale.id}")
        Text("Amount: $${sale.amount}")
        Text("Card Type: ${sale.cardType}")
        Text("Time: ${sale.timestamp}")
        Text("Status: ${sale.status}")
        Text("TxID: ${sale.transactionId}")
    }
}
