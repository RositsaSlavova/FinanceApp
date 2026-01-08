package bg.tu_varna.si.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.R
import bg.tu_varna.si.ui.theme.FinTextSecondary
import bg.tu_varna.si.utils.formatMoney

@Composable
fun BalanceRow(totalBalance: Double, totalExpense: Double, hidden: Boolean, purple: Color) {
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