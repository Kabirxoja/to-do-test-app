package uz.kabir.todolist.ui.navigation

sealed class NavRoute(val route: String) {
    object Main : NavRoute("main")
    object Add : NavRoute("add")
}