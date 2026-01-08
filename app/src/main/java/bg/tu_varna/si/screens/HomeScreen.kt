package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import bg.tu_varna.si.R
import bg.tu_varna.si.ui.theme.*

// --------- UI State ---------
data class HomeUiState(
    val greetingTitle: String = "Hi, Welcome Back",
    val greetingSubtitle: String = "Ready to save, Queen",
    val totalBalance: Double = 7783.00,
    val totalExpense: Double = 1187.40,
    val budgetLimit: Double = 20000.00,
    val budgetSpentPercent: Int = 30,
    val amountsHidden: Boolean = false,
    val selectedPeriod: PeriodTab = PeriodTab.MONTHLY,
    val recentTransactions: List<UiTransaction> = listOf(
        UiTransaction("Salary", "18:27 - April 30", "Monthly", +4000.00, TxnIcon.WORK),
        UiTransaction("Groceries", "17:00 - April 24", "Pantry", -100.00, TxnIcon.CART),
        UiTransaction("Rent", "8:30 - April 15", "Rent", -674.40, TxnIcon.HOME),
    )
)

enum class PeriodTab { DAILY, WEEKLY, MONTHLY }

data class UiTransaction(
    val title: String,
    val subtitle: String,
    val tag: String,
    val amount: Double,
    val icon: TxnIcon
)

enum class TxnIcon { WORK, CART, HOME }

// --------- Screen ---------
@Composable
fun HomeScreen(
    state: HomeUiState = HomeUiState(),
    onPeriodChange: (PeriodTab) -> Unit = {},
    onRevealClick: () -> Unit = {}
) {
    // ВЕЧЕ ИЗПОЛЗВАМЕ КОНСТАНТИТЕ ОТ Color.kt
    val turquoise = FinGreen
    val lightBg = FinBg
    val purple = FinExpenseBlue

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(turquoise)
            .verticalScroll(scrollState)
    ) {
        Header(state.greetingTitle, state.greetingSubtitle)
        Spacer(Modifier.height(16.dp))
        BalanceRow(state.totalBalance, state.totalExpense, state.amountsHidden, purple)
        Spacer(Modifier.height(16.dp))
        ProgressSection(state.budgetSpentPercent, state.budgetLimit, state.amountsHidden)
        Spacer(Modifier.height(12.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = lightBg,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, bottom = 100.dp)
            ) {
                WeeklySavingsCard(state.amountsHidden)
                Spacer(Modifier.height(20.dp))
                PeriodTabs(state.selectedPeriod, turquoise, lightBg, onPeriodChange)
                Spacer(Modifier.height(12.dp))
                TransactionsList(state.recentTransactions, state.amountsHidden)
            }
        }
    }
}

// --------- Components ---------

@Composable
private fun Header(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 48.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = FinTextPrimary)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, fontSize = 14.sp, color = FinTextSecondary)
        }
    }
}

@Composable
private fun BalanceRow(totalBalance: Double, totalExpense: Double, hidden: Boolean, purple: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(painterResource(R.drawable.ic_income), null, Modifier.size(20.dp), FinTextSecondary)
                Text("Total Balance", fontSize = 13.sp, color = FinTextSecondary)
            }
            Spacer(Modifier.height(8.dp))
            Text(formatMoney(totalBalance, "$", hidden), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Box(modifier = Modifier.width(1.dp).height(70.dp).background(Color.White.copy(alpha = 0.4f)))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(painterResource(R.drawable.ic_expense), null, Modifier.size(20.dp), FinTextSecondary)
                Text("Total Expense", fontSize = 13.sp, color = FinTextSecondary)
            }
            Spacer(Modifier.height(8.dp))
            Text("-${formatMoney(totalExpense, "$", hidden)}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = purple)
        }
    }
}

@Composable
private fun ProgressSection(percent: Int, limit: Double, hidden: Boolean) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(20.dp)).background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(percent / 100f).clip(RoundedCornerShape(20.dp)).background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (percent > 15) Text("$percent%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Box(modifier = Modifier.fillMaxHeight().weight(1f), contentAlignment = Alignment.CenterEnd) {
                Text(formatMoney(limit, "$", hidden), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(end = 16.dp))
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Outlined.CheckBox, null, Modifier.size(18.dp), Color.Black)
            Text("$percent% Of Your Expenses, Looks Good.", fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
private fun WeeklySavingsCard(hidden: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = FinGreen)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(90.dp)) {
                Box(
                    modifier = Modifier.size(80.dp)
                        .drawBehind {
                            val strokeWidth = 8.dp.toPx()
                            drawArc(Color.White, 90f, 180f, false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
                            drawArc(FinExpenseBlue, 270f, 180f, false, style = Stroke(strokeWidth, cap = StrokeCap.Round))
                        }
                        .background(FinGreenDark, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painterResource(R.drawable.ic_car), null, Modifier.size(44.dp), Color.Black)
                }
                Spacer(Modifier.height(12.dp))
                Text("Savings On Goals", fontSize = 13.sp, color = FinTextSecondary)
            }

            Box(modifier = Modifier.width(2.dp).height(130.dp).background(Color.White.copy(alpha = 0.5f)))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                // Revenue
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(painterResource(R.drawable.ic_salary), null, Modifier.size(40.dp), Color.Black)
                    Column {
                        Text("Revenue Last Week", fontSize = 11.sp, color = Color.Black)
                        Text(formatMoney(4000.0, "$", hidden), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.5f))
                Spacer(Modifier.height(16.dp))
                // Food
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(painterResource(R.drawable.ic_food), null, Modifier.size(40.dp), Color.Black)
                    Column {
                        Text("Food Last Week", fontSize = 11.sp, color = Color.Black)
                        Text("-${formatMoney(100.0, "$", hidden)}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FinExpenseBlue)
                    }
                }
            }
        }
    }
}

@Composable
private fun PeriodTabs(selected: PeriodTab, turquoise: Color, lightBg: Color, onSelected: (PeriodTab) -> Unit) {
    var selectedTab by remember { mutableStateOf(2) }
    val tabs = listOf("Daily", "Weekly", "Monthly")

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            tabs.forEachIndexed { index, title ->
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(26.dp))
                        .background(if (selectedTab == index) turquoise else Color.Transparent)
                        .clickable { selectedTab = index }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(title, fontSize = 14.sp, fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selectedTab == index) Color.Black else FinTextSecondary)
                }
            }
        }
    }
}

@Composable
private fun TransactionsList(transactions: List<UiTransaction>, hidden: Boolean) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        transactions.forEach { TransactionItem(it, hidden) }
    }
}

@Composable
private fun TransactionItem(tx: UiTransaction, hidden: Boolean) {
    val turquoise = FinGreen
    val dateBlue = FinBlue

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Икона
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    when (tx.icon) {
                        TxnIcon.WORK -> FinBlueLight
                        TxnIcon.CART -> FinBlue
                        TxnIcon.HOME -> FinDarkBlue
                    },
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = when (tx.icon) {
                        TxnIcon.WORK -> R.drawable.ic_salary
                        TxnIcon.CART -> R.drawable.ic_groceries
                        TxnIcon.HOME -> R.drawable.ic_rent
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        // 2. Текстов блок - БЕЗ МЯСТО МЕЖДУ ТЕКСТОВЕТЕ
        Column(
            modifier = Modifier.weight(1f),
            // Използваме Arrangement.spacedBy с отрицателна стойност, ако искаш да ги залепиш още повече
            verticalArrangement = Arrangement.spacedBy((-2).dp)
        ) {
            Text(
                text = tx.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1
            )
            Text(
                text = tx.subtitle,
                fontSize = 8.sp,
                color = dateBlue,
                maxLines = 1
            )
        }

        // 3. Първа линия
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(30.dp)
                .background(turquoise)
        )

        // 4. Таг
        Text(
            text = tx.tag,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.widthIn(min = 60.dp),
            textAlign = TextAlign.Center
        )

        // 5. Втора линия
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(30.dp)
                .background(turquoise)
        )

        // 6. Сума
        Text(
            text = if (tx.amount < 0) "-${formatMoney(kotlin.math.abs(tx.amount), "$", hidden)}"
            else formatMoney(tx.amount, "$", hidden),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = if (tx.amount < 0) dateBlue else Color.Black,
            modifier = Modifier.widthIn(min = 80.dp),
            textAlign = TextAlign.End
        )
    }
}

private fun formatMoney(amount: Double, currency: String, hidden: Boolean): String {
    if (hidden) return "•••• $currency"
    val nf = NumberFormat.getNumberInstance(Locale.US).apply { minimumFractionDigits = 2; maximumFractionDigits = 2 }
    return "$currency${nf.format(amount)}"
}