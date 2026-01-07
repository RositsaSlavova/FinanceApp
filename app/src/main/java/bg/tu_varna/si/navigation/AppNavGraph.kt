package bg.tu_varna.si.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        startDestination = Route.Home.route // временно; после ще го върнем на Login
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
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.AddTransaction.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("+", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when (route) {
                Route.Home.route -> {
                    HomeScreen(
                        state = HomeUiState(amountsHidden = false),
                        onNotificationsClick = { navController.navigate(Route.Notifications.route) },
                        onRevealClick = { /* по-късно biometric */ }
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
