package com.example.paymentappsimulation.presentation.dashboard.sale

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.paymentappsimulation.data.util.PreferenceManager
import com.example.paymentappsimulation.navigation.Screen
import com.example.paymentappsimulation.ui.theme.GreenDark
import com.example.paymentappsimulation.ui.theme.RedDark
import kotlinx.coroutines.delay

@Composable
fun SaleScreen(navController: NavController) {
    val viewModel: SaleViewModel = hiltViewModel()
    val paymentState by viewModel.paymentState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("") }
    var selectedTip by remember { mutableStateOf<Double?>(null) }

    val numericAmount = amount.toDoubleOrNull() ?: 0.0
    val tipAmount = selectedTip?.let { numericAmount * it } ?: 0.0
    val totalAmount = numericAmount + tipAmount

    val context = LocalContext.current


    LaunchedEffect(paymentState) {
        when (paymentState) {
            is PaymentResponseState.Loading -> {
                showDialog.value = true
                delay(3000)
            }
            is PaymentResponseState.Result -> {
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Total: $${"%.2f".format(totalAmount)}",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(75.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(0.15, 0.20, 0.30).forEach { tip ->
                val enabled = numericAmount > 0
                Button(
                    onClick = { selectedTip = tip },
                    enabled = enabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTip == tip) GreenDark else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("${(tip * 100).toInt()}%")
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            val buttons = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("C", "0", ".")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "C" -> {
                                        amount = ""
                                        selectedTip = null
                                    }
                                    "." -> if (!amount.contains(".")) amount += "."
                                    else -> amount += label
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        ) {
                            Text(label, fontSize = 20.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val cardTypes = listOf("TAP", "CHIP", "MANUEL")
                val randomCardType = cardTypes.random()

                viewModel.makePayment(
                    amount = numericAmount,
                    tip = tipAmount,
                    cardType = randomCardType
                )
            },
            enabled = numericAmount > 0,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Pay", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
              viewModel.cancelPayment(navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedDark
            )

        ) {
            Text("Cancel", fontSize = 20.sp)
        }
    }

    if (showDialog.value) {
        val isSuccess = paymentState is PaymentResponseState.Result &&
                (paymentState as PaymentResponseState.Result).response.status == "approved"

        val message = (paymentState as? PaymentResponseState.Result)?.response?.message ?: ""

        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = when (paymentState) {
                        is PaymentResponseState.Loading -> "Processing Payment"
                        is PaymentResponseState.Result -> if (isSuccess) "Success" else "Failed"
                        else -> ""
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = when {
                        isSuccess -> GreenDark
                        paymentState is PaymentResponseState.Result -> RedDark
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (paymentState) {
                        is PaymentResponseState.Loading -> CircularProgressIndicator()
                        is PaymentResponseState.Result -> {

                            if (!isSuccess) {
                                Text(
                                    text = message,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }

                            if (isSuccess) {
                                val name = PreferenceManager.getUsername(context)

                                Text(


                                    text = "Receipt:\nMerchant Name: ${name}\nAmount: $${"%.2f".format(totalAmount)}\nCard: TAP\nStatus: Approved",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        else -> {}
                    }
                }
            },
            confirmButton = {
                when {
                    paymentState is PaymentResponseState.Loading -> {}
                    isSuccess -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                showDialog.value = false
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(Screen.NewSale.route) { inclusive = true }
                                }
                                viewModel.resetState()
                            }) {
                                Text("Print")
                            }

                            Button(onClick = {
                                showDialog.value = false
                                navController.navigate(Screen.Dashboard.route) {
                                    popUpTo(Screen.NewSale.route) { inclusive = true }
                                }
                                viewModel.resetState()
                            }) {
                                Text("E-Copy")
                            }
                        }
                    }

                    else -> {
                        Button(onClick = {
                            showDialog.value = false
                            viewModel.resetState()
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        )
    }
}
