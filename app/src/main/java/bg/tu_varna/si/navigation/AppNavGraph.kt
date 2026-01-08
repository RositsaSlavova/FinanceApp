package bg.tu_varna.si.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import bg.tu_varna.si.screens.*

@Composable
fun AppRoot() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Login.route) {
            LoginScreen()
        }

        // Екраните от bottom nav
        composable(Route.Home.route) { MainScaffold(navController) }
        composable(Route.Transactions.route) { MainScaffold(navController) }
        composable(Route.Analysis.route) { MainScaffold(navController) }
        composable(Route.Categories.route) { MainScaffold(navController) }

        // Екрани извън bottom nav
        composable(Route.AddTransaction.route) { AddTransactionScreen() }
        composable(Route.Budget.route) { BudgetScreen() }
        composable(Route.Notifications.route) { NotificationsScreen() }
    }
}

@Composable
private fun MainScaffold(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val route = currentDestination?.route

    Scaffold(
        bottomBar = {
            ModernBottomBar(
                currentRoute = route,
                onNavigate = { destination ->
                    navController.navigate(destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.AddTransaction.route) },
                containerColor = Color(0xFF1DD1A1),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("+", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when (route) {
                Route.Home.route -> {
                    HomeScreen(
                        state = HomeUiState(amountsHidden = false),
                        onNotificationsClick = { navController.navigate(Route.Notifications.route) }
                    )
                }
                Route.Transactions.route -> TransactionsScreen()
                Route.Analysis.route -> AnalysisScreen()
                Route.Categories.route -> CategoriesScreen()
                else -> HomeScreen(state = HomeUiState())
            }
        }
    }
}

@Composable
private fun ModernBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFE8F8F5),
        tonalElevation = 0.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color(0xFF1DD1A1) else Color.Black.copy(alpha = 0.5f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1DD1A1),
                    unselectedIconColor = Color.Black.copy(alpha = 0.5f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}