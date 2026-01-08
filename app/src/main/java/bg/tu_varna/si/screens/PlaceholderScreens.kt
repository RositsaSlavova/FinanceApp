package bg.tu_varna.si.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable fun LoginScreen() = PlaceholderScreen("Login")
@Composable fun TransactionsScreen() = PlaceholderScreen("Transactions")
@Composable fun AnalysisScreen() = PlaceholderScreen("Analysis")
@Composable fun CategoriesScreen() = PlaceholderScreen("Categories")
@Composable fun AddTransactionScreen() = PlaceholderScreen("Add transaction")
@Composable fun BudgetScreen() = PlaceholderScreen("Budget")
