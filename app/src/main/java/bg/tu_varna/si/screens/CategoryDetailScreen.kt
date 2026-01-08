package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ТОВА ОПРАВЯ ГРЕШКАТА ЗА 'K'
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.components.* // Импортира TopBar, BalanceRow, MonthHeader и т.н.
import bg.tu_varna.si.data.foodTransactions
import bg.tu_varna.si.ui.theme.*

@Composable
fun CategoryDetailScreen(
    categoryName: String,
    onBackClick: () -> Unit
) {
    val filteredTransactions = remember(categoryName) {
        if (categoryName == "Food") foodTransactions else emptyList()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(FinGreen)) {
        TransactionTopBar(title = categoryName, onBackClick = onBackClick)

        BalanceRow(7783.0, 1187.40, false, FinExpenseBlue)
        Spacer(Modifier.height(16.dp))
        ProgressSection(30, 20000.0, false)
        Spacer(Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = FinBg,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                // Използваме Column, за да разделим пространството вертикално
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Списъкът заема цялото налично място, ОСВЕН това за бутона
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f) // Това казва на списъка: "вземи всичкото място до бутона"
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(
                            top = 24.dp,
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 8.dp
                        )
                    ) {
                        item { MonthHeaderWithCalendar("April") }

                        items(filteredTransactions) { tx ->
                            TransactionItem(tx, hidden = false)
                        }
                    }

                    // 2. Бутонът стои в отделна секция под списъка
                    // Така нищо не може да мине под него
                    Button(
                        onClick = { /* Add logic */ },
                        modifier = Modifier
                            .padding(vertical = 16.dp) // Разстояние от списъка и от BottomBar
                            .height(54.dp)
                            .fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreen),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Add Expenses", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}