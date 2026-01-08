package bg.tu_varna.si.utils

import java.text.NumberFormat
import java.util.Locale

fun formatMoney(amount: Double, currency: String, hidden: Boolean): String {
    if (hidden) return "•••• $currency"
    val nf = NumberFormat.getNumberInstance(Locale.GERMAN).apply { minimumFractionDigits = 2; maximumFractionDigits = 2 }
    return "$currency${nf.format(amount)}"
}