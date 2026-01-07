package bg.tu_varna.si.navigation

sealed class Route(val route: String) {
    data object Login : Route("login")
    data object Home : Route("home")
    data object Transactions : Route("transactions")
    data object Analysis : Route("analysis")
    data object Categories : Route("categories")

    data object AddTransaction : Route("add_transaction")
    data object Budget : Route("budget")
    data object Notifications : Route("notifications")
}
