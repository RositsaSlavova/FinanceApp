package bg.tu_varna.si.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.si.ui.theme.FinanceTheme
import java.text.NumberFormat
import java.util.Locale

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
    state: HomeUiState,
    onNotificationsClick: () -> Unit = {},
    onPeriodChange: (PeriodTab) -> Unit = {},
    onRevealClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(bottom = 0.dp)
        ) {
            Header(state.greetingTitle, state.greetingSubtitle, onNotificationsClick)

            BalanceRow(state.totalBalance, state.totalExpense, state.amountsHidden, "$")

            BudgetProgress(state.budgetSpentPercent, state.budgetLimit, state.amountsHidden, "$")

            InfoLine("${state.budgetSpentPercent}% Of Your Expenses, Looks Good.")

            Spacer(Modifier.height(20.dp))

            WeeklyCard(state.amountsHidden, "$")

            Spacer(Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
            ) {
                Column(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)) {
                    PeriodSelector(state.selectedPeriod, onPeriodChange)
                    Spacer(Modifier.height(16.dp))
                    TransactionsList(state.recentTransactions, state.amountsHidden, "$")
                    Spacer(Modifier.height(80.dp))
                }
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
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
        IconButton(
            onClick = onNotificationsClick,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun BalanceRow(totalBalance: Double, totalExpense: Double, hidden: Boolean, currency: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BalanceCard(
            "Total Balance",
            formatMoney(totalBalance, currency, hidden),
            false,
            modifier = Modifier.weight(1f)
        )
        BalanceCard(
            "Total Expense",
            formatMoney(-totalExpense, currency, hidden),
            true,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun BalanceCard(label: String, value: String, isExpense: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isExpense) Color(0xFF1E9B8E) else Color(0xFF2DD0B4))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isExpense) Icons.Filled.ShoppingCart else Icons.Filled.AccountCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleLarge,
            color = if (isExpense) Color(0xFF00D4FF) else Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BudgetProgress(
    percent: Int,
    limit: Double,
    amountsHidden: Boolean,
    currency: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1A1A1A))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "$percent%",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .height(32.dp)
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percent.coerceIn(0, 100) / 100f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1A1A1A))
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = formatMoney(limit, currency, amountsHidden),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoLine(text: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "✓",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(10.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
private fun WeeklyCard(hidden: Boolean, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF2DD0B4))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left side - Savings icon and text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Savings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "On Goals",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Vertical divider
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(100.dp)
                        .background(Color.White.copy(alpha = 0.3f))
                )

                // Right side - Revenue and Food
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Revenue Last Week",
                                color = Color.White.copy(alpha = 0.9f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Text(
                        formatMoney(4000.0, currency, hidden),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.3f))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Food Last Week",
                                color = Color.White.copy(alpha = 0.9f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Text(
                        formatMoney(-100.0, currency, hidden),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun PeriodSelector(
    selected: PeriodTab,
    onSelected: (PeriodTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFE8F5F3))
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PeriodChip(
            text = "Daily",
            selected = selected == PeriodTab.DAILY
        ) { onSelected(PeriodTab.DAILY) }

        PeriodChip(
            text = "Weekly",
            selected = selected == PeriodTab.WEEKLY
        ) { onSelected(PeriodTab.WEEKLY) }

        PeriodChip(
            text = "Monthly",
            selected = selected == PeriodTab.MONTHLY
        ) { onSelected(PeriodTab.MONTHLY) }
    }
}

@Composable
private fun PeriodChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (selected) Color(0xFF24D0B1)
        else Color.Transparent

    val textColor =
        if (selected) Color.White
        else Color(0xFF666666)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun TransactionsList(
    transactions: List<UiTransaction>,
    hidden: Boolean,
    currency: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        transactions.forEach {
            TransactionRow(it, hidden, currency)
            if (it != transactions.last()) {
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun TransactionRow(tx: UiTransaction, hidden: Boolean, currency: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    when (tx.icon) {
                        TxnIcon.WORK -> Color(0xFF4D9EFF)
                        TxnIcon.CART -> Color(0xFF4D9EFF)
                        TxnIcon.HOME -> Color(0xFF4D9EFF)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (tx.icon) {
                    TxnIcon.WORK -> Icons.Filled.AccountCircle
                    TxnIcon.CART -> Icons.Filled.ShoppingCart
                    TxnIcon.HOME -> Icons.Filled.Home
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                tx.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                tx.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF4D9EFF)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                tx.tag,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                formatMoney(tx.amount, currency, hidden),
                color = if (tx.amount < 0) Color(0xFF00D4FF) else Color(0xFF24D0B1),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFE8F5F3))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(Icons.Filled.Home, true)
        BottomNavItem(Icons.Filled.ShoppingCart, false)
        BottomNavItem(Icons.Filled.AccountCircle, false)
        BottomNavItem(Icons.Filled.Notifications, false)
        BottomNavItem(Icons.Filled.AccountCircle, false)
    }
}

@Composable
private fun BottomNavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (selected) Color(0xFF24D0B1) else Color.Transparent)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color.White else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

// --------- Helpers ---------
private fun formatMoney(amount: Double, currency: String, hidden: Boolean): String {
    if (hidden) return "•••• $currency"
    val nf = NumberFormat.getNumberInstance(Locale("bg", "BG")).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    val sign = if (amount < 0) "-" else ""
    return "$sign${nf.format(kotlin.math.abs(amount))} $currency"
}

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    HomeScreen(HomeUiState())
}