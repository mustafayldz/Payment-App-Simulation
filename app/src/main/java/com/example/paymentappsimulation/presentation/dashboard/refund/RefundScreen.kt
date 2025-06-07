package com.example.paymentappsimulation.presentation.dashboard.refund

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.paymentappsimulation.data.model.PaymentEntity
import com.example.paymentappsimulation.presentation.dashboard.history.SaleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefundScreen(navController: NavController, viewModel: RefundViewModel = hiltViewModel()) {
    val sales by viewModel.sales.collectAsState()
    var txId by remember { mutableStateOf("") }

    var showConfirmDialog by remember { mutableStateOf<PaymentEntity?>(null) }
    var refundAmount by remember { mutableStateOf("") }

    LaunchedEffect(txId) {
        if (txId.isNotEmpty()) {
            viewModel.searchSalesByTxId(txId)
        }else {
            viewModel.clearSales()
        }
    }

    if (showConfirmDialog != null) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = null
                refundAmount = ""
            },
            title = { Text("Confirm Refund") },
            text = {
                Column {
                    Text("Enter refund amount:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = refundAmount,
                        onValueChange = { refundAmount = it },
                        label = { Text("Amount") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    enabled = refundAmount.toDoubleOrNull() != null,
                    onClick = {
                        val amount = refundAmount.toDoubleOrNull()
                        showConfirmDialog?.let { originalSale ->
                            if (amount != null && amount > 0) {
                                viewModel.refundSale(originalSale, amount)
                            }
                        }
                        showConfirmDialog = null
                        refundAmount = ""
                        txId = ""
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showConfirmDialog = null
                    refundAmount = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }




    Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Refund") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
        }

    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {

            OutlinedTextField(
                value = txId,
                onValueChange = { txId = it },
                label = { Text("Search with TxID") },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                items(sales) { sale ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .then(Modifier)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                SaleItem(sale)
                            }

                            Button(
                                onClick = {
                                    showConfirmDialog = sale
                                }
                            ) {
                                Text("Refund")
                            }
                        }
                        Divider(modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }

        }
    }
}
