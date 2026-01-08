package bg.tu_varna.si.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
        }
    ) { padding ->

        Surface(modifier = Modifier.padding(padding)) {
            when (route) {

                Route.Home.route -> {
                    HomeScreen(
                        state = HomeUiState(amountsHidden = false)
                    )
                }

                Route.Transactions.route -> {
                    TransactionScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                Route.Analysis.route -> {
                    AnalysisScreen()
                }

                Route.Categories.route -> {
                    CategoriesScreen()
                }

                else -> {
                    HomeScreen(state = HomeUiState())
                }
            }
        }
    }
}

@Composable
private fun ModernBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val turquoise = Color(0xFF1DD1A1)
    val lightBg = Color(0xFFE8F8F5)

    NavigationBar(
        containerColor = lightBg,
        tonalElevation = 0.dp,
        modifier = Modifier
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
                        modifier = Modifier.size(28.dp),  // По-големи икони
                        tint = if (selected) turquoise else Color.Black.copy(alpha = 0.4f)
                    )
                },
                // БЕЗ LABEL - махнато
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = turquoise,
                    unselectedIconColor = Color.Black.copy(alpha = 0.4f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}