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

// --------- UI State ---------
data class HomeUiState(
    val greetingTitle: String = "Hi, Welcome Back",
    val greetingSubtitle: String = "Good Morning",
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
    onNotificationsClick: () -> Unit = {},
    onPeriodChange: (PeriodTab) -> Unit = {},
    onRevealClick: () -> Unit = {}
) {
    val turquoise = Color(0xFF1DD1A1)
    val lightBg = Color(0xFFE8F8F5)
    val purple = Color(0xFF667EEA)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(turquoise)
            .verticalScroll(scrollState)
    ) {
        // Header
        Header(state.greetingTitle, state.greetingSubtitle, onNotificationsClick)

        Spacer(Modifier.height(16.dp))

        // Balance Row
        BalanceRow(state.totalBalance, state.totalExpense, state.amountsHidden, purple)

        Spacer(Modifier.height(16.dp))

        // Progress Bar
        ProgressSection(state.budgetSpentPercent, state.budgetLimit, state.amountsHidden)

        Spacer(Modifier.height(12.dp))

        // БЕЛИЯТ SECTION ЗАПОЧВА ТУК (веднага след check message)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = lightBg,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, bottom = 100.dp)
            ) {
                // Savings Card (вече Е В БЕЛИЯ SECTION)
                WeeklySavingsCard(state.amountsHidden)

                Spacer(Modifier.height(20.dp))

                // Period Tabs
                PeriodTabs(state.selectedPeriod, turquoise, lightBg, onPeriodChange)

                Spacer(Modifier.height(12.dp))  // ПО-МАЛКО spacing

                // Transactions List
                TransactionsList(state.recentTransactions, state.amountsHidden)
            }
        }
    }
}

// --------- Components ---------
@Composable
private fun Header(title: String, subtitle: String, onNotificationsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 48.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(4.dp))
            Text(
                subtitle,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }

        IconButton(
            onClick = onNotificationsClick,
            modifier = Modifier
                .size(50.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.Black,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
private fun BalanceRow(
    totalBalance: Double,
    totalExpense: Double,
    hidden: Boolean,
    purple: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Total Balance
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Outlined.ArrowUpward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black.copy(alpha = 0.6f)
                )
                Text(
                    "Total Balance",
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                formatMoney(totalBalance, "$", hidden),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Vertical divider
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(70.dp)
                .background(Color.White.copy(alpha = 0.4f))
        )

        // Total Expense
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Outlined.ArrowDownward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black.copy(alpha = 0.6f)
                )
                Text(
                    "Total Expense",
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "-${formatMoney(totalExpense, "$", hidden)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF667EEA)
            )
        }
    }
}

@Composable
private fun ProgressSection(
    percent: Int,
    limit: Double,
    hidden: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        // Progress Bar - ВСИЧКО В ЕДНА ЛИНИЯ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Black progress part
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percent / 100f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (percent > 15) {
                    Text(
                        "$percent%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // White remaining part with limit amount
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    formatMoney(limit, "$", hidden),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Status message
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Outlined.CheckBox,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.Black
            )
            Text(
                "$percent% Of Your Expenses, Looks Good.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun WeeklySavingsCard(hidden: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1DD1A1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left - Car icon and labels
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(90.dp)
            ) {
                // Car icon с наситен син кръг
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .drawBehind {
                            val strokeWidth = 8.dp.toPx()
                            drawArc(
                                color = Color.White,
                                startAngle = 90f,
                                sweepAngle = 180f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = Color(0xFF667EEA),
                                startAngle = 270f,
                                sweepAngle = 180f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }
                        .background(Color(0xFF1DD1A1), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.DirectionsCar,
                        contentDescription = null,
                        modifier = Modifier.size(44.dp),
                        tint = Color.Black
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Savings",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "On Goals",
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }

            // Vertical divider
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(130.dp)
                    .background(Color.White.copy(alpha = 0.5f))
            )

            // Right - Stats
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Revenue
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_salary),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Revenue Last Week",
                            fontSize = 11.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(0.5.dp))
                        Text(
                            formatMoney(4000.0, "$", hidden),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(Modifier.height(16.dp))

                // Food
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Restaurant,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Food Last Week",
                            fontSize = 11.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(0.5.dp))
                        Text(
                            "-${formatMoney(100.0, "$", hidden)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF667EEA)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PeriodTabs(
    selected: PeriodTab,
    turquoise: Color,
    lightBg: Color,
    onSelected: (PeriodTab) -> Unit
) {
    var selectedTab by remember { mutableStateOf(2) }
    val tabs = listOf("Daily", "Weekly", "Monthly")

    // БЯЛ BACKGROUND КАТО КАРТА
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(26.dp))
                        .background(
                            if (selectedTab == index) turquoise else Color.Transparent
                        )
                        .clickable { selectedTab = index }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        title,
                        fontSize = 14.sp,
                        fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selectedTab == index) Color.Black else Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionsList(
    transactions: List<UiTransaction>,
    hidden: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),  // БЕЗ horizontal padding - директно на background
        verticalArrangement = Arrangement.spacedBy(8.dp)  // ПО-МАЛКО spacing
    ) {
        transactions.forEach { tx ->
            TransactionItem(tx, hidden)
        }
    }
}

@Composable
private fun TransactionItem(tx: UiTransaction, hidden: Boolean) {
    // БЕЗ CARD - директно на background-а
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon - По-голяма кръгла икона
        Box(
            modifier = Modifier
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        when (tx.icon) {
                            TxnIcon.WORK -> Color(0xFF6BA3E8)  // Светъл син
                            TxnIcon.CART -> Color(0xFF4A90E2)  // Среден син
                            TxnIcon.HOME -> Color(0xFF0066FF)  // Тъмен син
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    when (tx.icon) {
                        TxnIcon.WORK -> Icons.Outlined.Payments
                        TxnIcon.CART -> Icons.Outlined.ShoppingCart
                        TxnIcon.HOME -> Icons.Outlined.Home
                    },
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = Color.White
                )
            }
        }

        // Title and Date
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                tx.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            //Spacer(Modifier.height(1.dp))
            Text(
                tx.subtitle,
                fontSize = 8.sp,
                color = Color(0xFF00D09E)  // Син
            )
        }

        // ПЪРВИ DIVIDER (turquoise)
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(45.dp)
                .background(Color(0xFF1DD1A1))
        )

        // Category (центрирана между dividers)
        Text(
            tx.tag,
            fontSize = 10.sp,
            color = Color.Black,
            modifier = Modifier.widthIn(min = 70.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // ВТОРИ DIVIDER (turquoise)
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(45.dp)
                .background(Color(0xFF00D09E))
        )

        // Amount
        Text(
            if (tx.amount < 0) "-${formatMoney(kotlin.math.abs(tx.amount), "$", hidden)}"
            else formatMoney(tx.amount, "$", hidden),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (tx.amount < 0) Color(0xFF4A90E2) else Color.Black,  // НЕГАТИВНИ СА СИНИ!
            modifier = Modifier.widthIn(min = 90.dp),
            textAlign = TextAlign.End
        )
    }
}

// --------- Helper ---------
private fun formatMoney(amount: Double, currency: String, hidden: Boolean): String {
    if (hidden) return "•••• $currency"
    val nf = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return "$currency${nf.format(amount)}"
}