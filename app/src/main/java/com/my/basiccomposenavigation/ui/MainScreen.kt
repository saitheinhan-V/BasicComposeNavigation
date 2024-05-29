package com.my.basiccomposenavigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.my.basiccomposenavigation.Accounts
import com.my.basiccomposenavigation.Bills
import com.my.basiccomposenavigation.MainNavHost
import com.my.basiccomposenavigation.Overview
import com.my.basiccomposenavigation.R
import com.my.basiccomposenavigation.TabRowScreens
import com.my.basiccomposenavigation.navigateSingleTopTo
import com.my.basiccomposenavigation.ui.model.MainForm
import com.my.basiccomposenavigation.ui.utils.NavigationInstructor
import com.my.basiccomposenavigation.ui.utils.Screen
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel


@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val vm: MainViewModel = hiltViewModel()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentTab = TabRowScreens.find { it.route == currentDestination?.route } ?: Overview

    NavigationInstructor(
        instructor = vm.appNavigator.instructor,
        controller = navController,
    )



    Scaffold(
//        topBar = {
//            TabRows(
//                tabList = TabRowScreens,
//                onTabSelected = { screen ->
////                    navController.navigateSingleTopTo(screen.route)
////                                navController.navigate(screen.route)
//                    navController.navigate(screen.route) {
//                        // Pop up to the start destination of the graph to
//                        // avoid building up a large stack of destinations
//                        // on the back stack as users select items
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
//                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
////                        restoreState = false
//                    }
//                },
//                currentTab = currentTab
//            )
//        }
        bottomBar = {
            NavigationBar {
                TabRowScreens.forEach {
                    val icon = when (it.route) {
                        Accounts.route -> R.drawable.ic_account
                        Bills.route -> R.drawable.ic_cash
                        else -> R.drawable.ic_home
                    }

                    NavigationBarItem(
                        selected = currentDestination?.route == it.route,
                        onClick = {
//                            val route = when(it.route){
//                                Bills.route -> Screen.BILL
//                                Accounts.route -> Screen.ACCOUNT
//                                else -> Screen.OVERVIEW
//                            }
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = "BottomBar Icon",
                            )
                        },
                        label = {
                            Text(
                                text = it.name
                            )
                        })
                }
            }
        }
    )
    { paddingValues ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )

    }

}