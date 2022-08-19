package com.senyor_o.usercrudapp.presentation.navigation

sealed class AppScreen(val route: String) {

    object MainScreen: AppScreen("main_screen")

    object EditScreen: AppScreen("edit?userId={userId}") {
        fun passId(userId: Int?): String{
            return "edit?userId=$userId"
        }
    }

}