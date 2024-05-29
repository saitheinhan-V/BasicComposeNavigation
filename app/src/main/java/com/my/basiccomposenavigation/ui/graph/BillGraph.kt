package com.my.basiccomposenavigation.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.my.basiccomposenavigation.Bills
import com.my.basiccomposenavigation.ui.components.Bill
import com.my.basiccomposenavigation.ui.components.BillScreen
import com.my.basiccomposenavigation.ui.utils.Screen
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel
import com.my.basiccomposenavigation.ui.viewModel.sharedVM

fun NavGraphBuilder.billGraph(
    controller: NavHostController
) {
    navigation(
        startDestination = Bills.route,
        route = Screen.BILL
    ) {
        composable(
            route = Bills.route
        ) {
            val vm = it.sharedVM<MainViewModel>(navController = controller)

            Bill(vm)
        }
    }
}