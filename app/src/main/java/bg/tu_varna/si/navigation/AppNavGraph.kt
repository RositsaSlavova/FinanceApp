package bg.tu_varna.si.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import bg.tu_varna.si.screens.*

@Composable
fun AppRoot() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Login.route) { LoginScreen() }

        // Основни екрани
        composable(Route.Home.route) { MainScaffold(navController, Route.Home.route) }
        composable(Route.Transactions.route) { MainScaffold(navController, Route.Transactions.route) }
        composable(Route.Analysis.route) { MainScaffold(navController, Route.Analysis.route) }
        composable(Route.Categories.route) { MainScaffold(navController, Route.Categories.route) }

        // Категории с параметър
        composable(
            route = Route.CategoryDetail.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("categoryName") ?: ""
            MainScaffold(navController, Route.CategoryDetail.route, name)
        }

        // Add Expense - СЪЩО в MainScaffold за Bottom Bar
        composable(Route.AddTransaction.route) {
            MainScaffold(navController, Route.AddTransaction.route)
        }
    }
}

@Composable
private fun MainScaffold(
    navController: NavHostController,
    currentRoute: String,
    categoryName: String? = null
) {
    Scaffold(
        bottomBar = {
            ModernBottomBar(
                currentRoute = currentRoute,
                onNavigate = { destination ->
                    if (currentRoute != destination) {
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when {
                currentRoute == Route.Home.route -> HomeScreen(state = HomeUiState(amountsHidden = false))
                currentRoute == Route.Transactions.route -> TransactionScreen(onBackClick = { navController.popBackStack() })
                currentRoute == Route.Analysis.route -> AnalysisScreen()
                currentRoute == Route.Categories.route -> CategoriesScreen(
                    onCategoryClick = { name -> navController.navigate(Route.CategoryDetail.createRoute(name)) },
                    onBackClick = { navController.popBackStack() }
                )
                currentRoute.startsWith("category_detail") -> CategoryDetailScreen(
                    categoryName = categoryName ?: "Category",
                    navController = navController // Подаваме го тук
                )
                currentRoute == Route.AddTransaction.route -> AddExpenseScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun ModernBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    val turquoise = Color(0xFF1DD1A1)
    NavigationBar(containerColor = Color(0xFFE8F8F5), tonalElevation = 0.dp) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(28.dp),
                        tint = if (selected) turquoise else Color.Black.copy(alpha = 0.4f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}