package bg.tu_varna.si.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = FinGreen,
    onPrimary = FinTextOnGreen,

    background = FinBg,
    onBackground = FinTextPrimary,

    surface = FinSurface,
    onSurface = FinTextPrimary,

    secondary = FinSurface2,
    onSecondary = FinTextPrimary,

    outline = FinTextSecondary,

    // Допълнителни цветове
    error = Color(0xFFDC3545),
    onError = Color.White
)

@Composable
fun FinanceAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    CompositionLocalProvider(
        LocalAppColors provides LightAppColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = AppShapes,
            content = content
        )
    }
}