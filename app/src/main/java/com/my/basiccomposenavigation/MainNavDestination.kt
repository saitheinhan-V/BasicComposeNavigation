package com.my.basiccomposenavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
    val name: String
}

/**
 * Rally app navigation destinations
 */
object Overview : Destination {
    override val icon = Icons.Filled.PieChart
    override val route = "overview_initial"
    override val name = "Overview"
}

object Accounts : Destination {
    override val icon = Icons.Filled.AttachMoney
    override val route = "accounts_initial"
    override val name = "Account"
}

object Bills : Destination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "bills_initial"
    override val name = "Bill"
}

val TabRowScreens = listOf(Overview, Accounts, Bills)
