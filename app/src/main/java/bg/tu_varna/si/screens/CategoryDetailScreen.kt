package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bg.tu_varna.si.components.*
import bg.tu_varna.si.data.foodTransactions
import bg.tu_varna.si.ui.theme.*
import bg.tu_varna.si.navigation.Route

@Composable
fun CategoryDetailScreen(
    categoryName: String,
    navController: NavController // ВАЖНО: Добавено тук
) {
    val filteredTransactions = remember(categoryName) {
        if (categoryName == "Food") foodTransactions else emptyList()
    }

    Column(modifier = Modifier.fillMaxSize().background(FinGreen)) {
        TransactionTopBar(title = categoryName, onBackClick = { navController.popBackStack() })

        BalanceRow(7783.0, 1187.40, false, FinExpenseBlue)
        Spacer(Modifier.height(16.dp))
        ProgressSection(30, 20000.0, false)
        Spacer(Modifier.height(24.dp))

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FinBg,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(24.dp)
                ) {
                    item { MonthHeaderWithCalendar("April") }
                    items(filteredTransactions) { tx -> TransactionItem(tx, hidden = false) }
                }

                Button(
                    // ОПРАВЕНО: Сега навигира правилно
                    onClick = { navController.navigate(Route.AddTransaction.route) },
                    modifier = Modifier.padding(vertical = 16.dp).height(54.dp).fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(containerColor = FinGreen),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Add Expenses", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}