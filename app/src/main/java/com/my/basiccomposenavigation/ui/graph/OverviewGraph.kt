package com.my.basiccomposenavigation.ui.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.my.basiccomposenavigation.Overview
import com.my.basiccomposenavigation.ui.components.Overview
import com.my.basiccomposenavigation.ui.utils.Screen
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel
import com.my.basiccomposenavigation.ui.viewModel.sharedVM


fun NavGraphBuilder.overviewGraph(
    controller: NavHostController
) {
    navigation(
        startDestination = Overview.route,
        route = Screen.OVERVIEW
    ) {
        composable(
            route = Overview.route
        ) {
            val vm = it.sharedVM<MainViewModel>(navController = controller)

//            val vm = hiltViewModel<MainViewModel>(it)
            Overview(vm)
        }
    }
}