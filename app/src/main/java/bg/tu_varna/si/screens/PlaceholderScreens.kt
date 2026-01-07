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

@Composable fun LoginScreen() = PlaceholderScreen("Вход")
@Composable fun TransactionsScreen() = PlaceholderScreen("Транзакции")
@Composable fun AnalysisScreen() = PlaceholderScreen("Анализ")
@Composable fun CategoriesScreen() = PlaceholderScreen("Категории")
@Composable fun AddTransactionScreen() = PlaceholderScreen("Добави транзакция")
@Composable fun BudgetScreen() = PlaceholderScreen("Бюджет")
@Composable fun NotificationsScreen() = PlaceholderScreen("Известия")
