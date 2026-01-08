package bg.tu_varna.si.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Route.Home.route, "Home", Icons.Outlined.Home),
    BottomNavItem(Route.Transactions.route, "Stats", Icons.Outlined.BarChart),
    BottomNavItem(Route.Analysis.route, "Transfer", Icons.Outlined.SwapHoriz),
    BottomNavItem(Route.Categories.route, "More", Icons.Outlined.Layers),
)