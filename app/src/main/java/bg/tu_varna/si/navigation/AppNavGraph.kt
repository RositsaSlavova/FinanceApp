package bg.tu_varna.si.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
        startDestination = Route.Home.route // Директно към Home (обвит в Scaffold)
    ) {
        composable(Route.Login.route) { LoginScreen() }

        // Всички основни екрани минават през MainScaffold
        composable(Route.Home.route) { MainScaffold(navController, Route.Home.route) }
        composable(Route.Transactions.route) { MainScaffold(navController, Route.Transactions.route) }
        composable(Route.Analysis.route) { MainScaffold(navController, Route.Analysis.route) }
        composable(Route.Categories.route) { MainScaffold(navController, Route.Categories.route) }

        // ДЕТАЙЛИТЕ: Също обвити в MainScaffold, за да има Bottom Bar!
        composable(
            route = Route.CategoryDetail.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("categoryName") ?: ""
            MainScaffold(navController, Route.CategoryDetail.route, name)
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
                    // Навигираме само ако не сме вече на същия екран
                    if (currentRoute != destination) {
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            // ТУК Е РЕШЕНИЕТО: Проверяваме конкретния маршрут без "else -> Home"
            when {
                currentRoute == Route.Home.route -> {
                    HomeScreen(state = HomeUiState(amountsHidden = false))
                }
                currentRoute == Route.Transactions.route -> {
                    TransactionScreen(onBackClick = { navController.popBackStack() })
                }
                currentRoute == Route.Analysis.route -> {
                    AnalysisScreen()
                }
                currentRoute == Route.Categories.route -> {
                    CategoriesScreen(
                        onCategoryClick = { name ->
                            navController.navigate(Route.CategoryDetail.createRoute(name))
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
                currentRoute.startsWith("category_detail") -> {
                    CategoryDetailScreen(
                        categoryName = categoryName ?: "Category",
                        onBackClick = { navController.popBackStack() }
                    )
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
        tonalElevation = 0.dp
    ) {
        bottomNavItems.forEach { item ->
            // BottomBar иконата свети, ако сме на съответния маршрут
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
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}