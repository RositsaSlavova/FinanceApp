package bg.tu_varna.si.data

import androidx.compose.ui.graphics.Color
import bg.tu_varna.si.R
import bg.tu_varna.si.screens.TxnIcon
import bg.tu_varna.si.screens.UiTransaction
import bg.tu_varna.si.ui.theme.* // Импортираме твоите цветове (FinGreen, FinBlue и т.н.)

// 1. Модел за категория
data class CategoryItem(
    val name: String,
    val iconRes: Int,
    val color: Color
)

// 2. Списък с всички категории, използващ ТВОИТЕ цветове от Color.kt
val allCategories = listOf(
    // Използваме FinExpenseBlue за активната или основната категория
    CategoryItem("Food", R.drawable.ic_food, FinExpenseBlue),
    CategoryItem("Transport", R.drawable.ic_food, FinBlue),
    CategoryItem("Medicine", R.drawable.ic_food, FinBlueLight),
    CategoryItem("Groceries", R.drawable.ic_groceries, FinDarkBlue),
    CategoryItem("Rent", R.drawable.ic_rent, FinDarkBlue),
    CategoryItem("Gifts", R.drawable.ic_food, FinBlueLight),
    CategoryItem("Savings", R.drawable.ic_food, FinGreenDark),
    CategoryItem("Entertainment", R.drawable.ic_food, FinBlue),
    CategoryItem("More", R.drawable.ic_food, FinBlueLight)
)

// 3. Данни за трансакциите (използват твоя модел UiTransaction)
val foodTransactions = listOf(
    UiTransaction("Dinner", "18:27 - April 30", "Dining", -26.00, TxnIcon.HOME),
    UiTransaction("Delivery Pizza", "15:00 - April 24", "Takeout", -18.35, TxnIcon.HOME),
    UiTransaction("Lunch", "12:30 - April 15", "Work", -15.40, TxnIcon.HOME),
    UiTransaction("Brunch", "9:30 - April 08", "Weekend", -12.13, TxnIcon.HOME)
)