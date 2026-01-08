package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.components.BalanceRow
import bg.tu_varna.si.components.ProgressSection
import bg.tu_varna.si.components.TransactionItem
import bg.tu_varna.si.components.TransactionTopBar
import bg.tu_varna.si.ui.theme.*

// --------- UI State за Transaction Screen ---------
data class TransactionUiState(
    val totalBalance: Double = 7783.00,
    val totalExpense: Double = 1187.40,
    val budgetLimit: Double = 20000.00,
    val budgetSpentPercent: Int = 30,
    val groupedTransactions: Map<String, List<UiTransaction>> = mapOf(
        "April" to listOf(
            UiTransaction("Salary", "18:27 - April 30", "Monthly", 4000.0, TxnIcon.WORK),
            UiTransaction("Groceries", "17:00 - April 24", "Pantry", -100.0, TxnIcon.CART),
            UiTransaction("Rent", "8:30 - April 15", "Rent", -674.40, TxnIcon.HOME),
            UiTransaction("Transport", "9:30 - April 08", "Fuel", -4.13, TxnIcon.CART)
        ),
        "March" to listOf(
            UiTransaction("Food", "19:30 - March 31", "Dinner", -70.40, TxnIcon.HOME)
        )
    )
)

@Composable
fun TransactionScreen(
    state: TransactionUiState = TransactionUiState(),
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FinGreen)
    ) {
        // 1. Top Bar с бутон за връщане
        TransactionTopBar(
            title = "Categories",
            onBackClick = onBackClick
        )

        // 2. Бялата карта с Total Balance (специфична за този екран)
        TotalBalanceWhiteCard(state.totalBalance)

        Spacer(Modifier.height(16.dp))

        // 3. Преизползваме BalanceRow и ProgressSection от Home
        // Забележка: Тук подаваме същите данни, за да съвпадат с прототипа
        BalanceRow(state.totalBalance, state.totalExpense, false, FinExpenseBlue)

        Spacer(Modifier.height(16.dp))

        ProgressSection(state.budgetSpentPercent, state.budgetLimit, false)

        Spacer(Modifier.height(24.dp))

        // 4. Списъкът с трансакциите (Белият контейнер)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = FinBg,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
            ) {
                state.groupedTransactions.forEach { (month, transactions) ->
                    // Заглавие на месеца (Sticky Header ефект)
                    item {
                        Text(
                            text = month,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                            color = Color.Black
                        )
                    }

                    // Трансакциите за този месец
                    items(transactions) { tx ->
                        // Използваме твоя готов TransactionItem
                        TransactionItem(tx, hidden = false)
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalBalanceWhiteCard(balance: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total Balance", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f))
            Text(
                "$${String.format("%,.2f", balance)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        }
    }
}