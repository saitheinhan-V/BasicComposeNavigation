package com.my.basiccomposenavigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.my.basiccomposenavigation.ui.components.AccountScreen
import com.my.basiccomposenavigation.ui.components.Bill
import com.my.basiccomposenavigation.ui.components.Detail
import com.my.basiccomposenavigation.ui.components.Overview
import com.my.basiccomposenavigation.ui.graph.accountGraph
import com.my.basiccomposenavigation.ui.graph.billGraph
import com.my.basiccomposenavigation.ui.graph.overviewGraph
import com.my.basiccomposenavigation.ui.utils.Screen
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel
import com.my.basiccomposenavigation.ui.viewModel.sharedVM

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.OVERVIEW,
        modifier = modifier,
        route = Screen.ROOT
    ) {
        accountGraph(navController)
        billGraph(navController)
        overviewGraph(navController)
//        navigation(
//            startDestination = Overview.route,
//            route = Screen.HOME
//        ) {
//            composable(
//                route = Overview.route,
////            arguments = listOf(navArgument("amount"){ type = NavType.StringType})
//            ) {
////            LaunchedEffect(key1 = Unit) {
////                val amount = it.arguments?.getString("amount")
////                navController.previousBackStackEntry?.savedStateHandle?.apply {
////                    "amount" to amount
////                }
////            }
////            val vm = it.sharedVM<MainViewModel>(navController = navController)
//
//                val parentEntry = remember {
//                    navController.getBackStackEntry(Overview.route)
//                }
//                val vm = hiltViewModel<MainViewModel>(parentEntry)
//                Overview(vm = vm)
//            }
//            composable(route = Accounts.route) {
//                AccountScreen()
//            }
//            composable(route = Bills.route) {
//                val vm = it.sharedVM<MainViewModel>(navController = navController)
//
//                Bill(vm = vm)
//            }
//
////        composable(route = Bills.route)
////        {backStackEntry ->
////            BillScreen(backStackEntry.arguments?.getString("amount")!!)
////        }
//
//        }

        //detail
        navigation(
            startDestination = Screen.DetailPage.getRoutePath(),
            route = Screen.DETAIL
        ) {
            composable(Screen.DetailPage.getRoutePath(),
                arguments = listOf(
                    navArgument(name = Screen.AMOUNT) {
                        type = NavType.StringType
                        defaultValue = "0"
                    }
                )
            ) {
                LaunchedEffect (key1 = Unit){
                    val amount = it.arguments?.getString(Screen.AMOUNT)
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        Screen.AMOUNT to amount
                    }
                }
                val parentEntry = remember {
                    navController.getBackStackEntry(Overview.route)
                }
                val vm = hiltViewModel<MainViewModel>(parentEntry)
//                val vm = it.sharedVM<MainViewModel>(navController = navController)

                Detail(vm = vm)
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }