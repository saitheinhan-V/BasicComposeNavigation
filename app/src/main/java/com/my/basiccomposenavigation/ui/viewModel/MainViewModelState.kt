package com.my.basiccomposenavigation.ui.viewModel

import com.my.basiccomposenavigation.ui.model.MainForm

data class MainViewModelState(
    val form: MainForm = MainForm()
){
    fun mainForm() = form
}