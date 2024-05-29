package com.my.basiccomposenavigation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.my.basiccomposenavigation.ui.model.MainForm
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel

@Composable
fun Bill(
    vm: MainViewModel
){
    val mainForm = vm.mainForm.collectAsState()

    BillScreen(mainForm = mainForm.value)
}

@Composable
fun BillScreen(
    mainForm: MainForm = MainForm()
){
    Column {
        Text(text = "This is Bill")
        Text(text = mainForm.amount)
    }
}