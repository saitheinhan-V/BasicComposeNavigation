package com.my.basiccomposenavigation.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.my.basiccomposenavigation.Accounts
import com.my.basiccomposenavigation.ui.components.AccountScreen
import com.my.basiccomposenavigation.ui.utils.Screen

fun NavGraphBuilder.accountGraph(
    controller: NavHostController
){
    navigation(
        startDestination = Accounts.route,
        route = Screen.ACCOUNT
    ){
        composable(
            route = Accounts.route
        ){
            AccountScreen()
        }
    }
}