package com.senyor_o.usercrudapp.presentation.navigation

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.senyor_o.usercrudapp.presentation.edit.EditScreen
import com.senyor_o.usercrudapp.presentation.main.MainScreen
import com.senyor_o.usercrudapp.presentation.main.MainScreenViewModel

@Composable
fun Navigation(
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: MainScreenViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = AppScreen.MainScreen.route
    ) {
        composable(route = AppScreen.MainScreen.route) {
            MainScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = AppScreen.EditScreen.route,
            arguments = listOf(
                navArgument(
                    name = "userId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            EditScreen(
                onNavigateBack = { message ->
                    navController.popBackStack().also {
                        viewModel.getUsers()
                        message?.let {
                            Toast.makeText(
                                context,
                                context.getText(message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }
    }
}