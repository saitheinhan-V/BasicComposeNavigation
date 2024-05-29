package com.my.basiccomposenavigation.ui.model

sealed interface DetailAction {

    data object BackPress: DetailAction
}