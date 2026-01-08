package bg.tu_varna.si.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.si.R
import bg.tu_varna.si.screens.TxnIcon
import bg.tu_varna.si.screens.UiTransaction
import bg.tu_varna.si.ui.theme.FinBlue
import bg.tu_varna.si.ui.theme.FinBlueLight
import bg.tu_varna.si.ui.theme.FinDarkBlue
import bg.tu_varna.si.ui.theme.FinGreen
import bg.tu_varna.si.utils.formatMoney

@Composable
fun TransactionItem(tx: UiTransaction, hidden: Boolean) {
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