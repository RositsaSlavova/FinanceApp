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
    BottomNavItem(Route.Analysis.route, "Analysis", Icons.Outlined.InsertChart),  // За analysis/stats
    BottomNavItem(Route.Transactions.route, "Transactions", Icons.Outlined.SwapHoriz),  // За transfers
    BottomNavItem(Route.Categories.route, "Categories", Icons.Outlined.Layers)  // За layers/categories
)