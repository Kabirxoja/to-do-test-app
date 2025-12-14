package uz.kabir.todolist.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.kabir.todolist.ui.screens.AddScreen
import uz.kabir.todolist.ui.screens.DetailScreen
import uz.kabir.todolist.ui.screens.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(NavRoute.Main.route){
            MainScreen(
                onNavigateToAdd = {
                    navController.navigate(NavRoute.Add.route)
                },
                onNavigateToDetail = { todoId ->
                    navController.navigate("detail/$todoId")
                }
            )
        }


        composable(NavRoute.Add.route) {
            AddScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            DetailScreen(
                todoId = todoId,
                onNavigateBack = { navController.popBackStack() }
            )
        }


    }
}