package bg.tu_varna.si.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val cardDark: Color,
    val cardText: Color,
    val cardSubtext: Color,
    val expense: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        cardDark = Color.Unspecified,
        cardText = Color.Unspecified,
        cardSubtext = Color.Unspecified,
        expense = Color.Unspecified
    )
}

val LightAppColors = AppColors(
    cardDark = FinDarkCard,
    cardText = FinDarkCardText,
    cardSubtext = FinDarkCardSubtext,
    expense = FinExpenseBlue
)

