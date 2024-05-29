package com.my.basiccomposenavigation.ui.model

sealed interface MainAction{

    data class ChangeAmount(val amount: String) : MainAction

    data object ClickBill : MainAction

    data object ClickAccount: MainAction

    data object ClickDetail: MainAction


}