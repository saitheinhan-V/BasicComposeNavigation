package com.my.basiccomposenavigation.ui.utils

sealed interface DetailScreen
sealed class Screen(val route: String){

    companion object{
        const val HOME = "home"
        const val DETAIL = "detail"
        const val ROOT = "root"

        const val AMOUNT = "amount"

        const val OVERVIEW = "overview"
        const val BILL = "bill"
        const val ACCOUNT = "account"
    }

    object DetailPage : Screen(route = "detail"), DetailScreen{

        fun passParam(amount: String): String{
            return this.route + "/$amount"
        }

        fun getRoutePath(): String{
            return this.route + "/{$AMOUNT}"
        }

    }
}