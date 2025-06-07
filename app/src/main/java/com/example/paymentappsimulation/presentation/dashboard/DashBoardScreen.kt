package com.example.paymentappsimulation.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.paymentappsimulation.presentation.dashboard.components.DashboardButton
import com.example.paymentappsimulation.ui.theme.RedDark


data class DashboardItem(val label: String, val icon: ImageVector, val route: String)

val dashboardItems = listOf(
    DashboardItem("New Sale", Icons.Default.Add, "new_sale"),
    DashboardItem("Refund", Icons.Default.Delete, "refund"),
    DashboardItem("Void", Icons.Default.Clear, "void"),
    DashboardItem("History", Icons.Default.Info, "history")
)

@Composable
fun DashboardScreen(navController: NavController,
                    viewModel: DashBoardViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dashboardItems) { item ->
                DashboardButton(
                    text = item.label,
                    icon = item.icon,
                    onClick = { navController.navigate(item.route) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.logout(context, navController) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = RedDark),
        ) {
            Text("Log Out")
        }

    }
}