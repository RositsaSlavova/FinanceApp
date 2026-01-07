package bg.tu_varna.si.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Route.Home.route, "Начало", Icons.Filled.Home),
    BottomNavItem(Route.Transactions.route, "Транзакции", Icons.Filled.List),
    BottomNavItem(Route.Analysis.route, "Анализ", Icons.Filled.Star),
    BottomNavItem(Route.Categories.route, "Категории", Icons.Filled.Category),
)
