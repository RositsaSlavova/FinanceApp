package bg.tu_varna.si.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = FinGreen,
    onPrimary = FinTextOnGreen,

    background = FinBg,
    onBackground = FinTextPrimary,

    surface = FinSurface,
    onSurface = FinTextPrimary,

    secondary = FinSurface2,
    onSecondary = FinTextPrimary,

    outline = FinTextSecondary
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