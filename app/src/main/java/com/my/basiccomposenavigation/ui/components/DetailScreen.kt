package com.my.basiccomposenavigation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.my.basiccomposenavigation.R
import com.my.basiccomposenavigation.ui.model.DetailAction
import com.my.basiccomposenavigation.ui.model.MainForm
import com.my.basiccomposenavigation.ui.viewModel.MainViewModel

@Composable
fun Detail(
    vm: MainViewModel
) {

    val mainForm = vm.mainForm.collectAsState()

    DetailScreen(
        mainForm = mainForm.value,
        onAction = vm::onActionDetail
    )
}

@Composable
fun DetailScreen(
    mainForm: MainForm,
    onAction: (DetailAction) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        topBar = {
            IconButton(
                onClick = {
                    onAction(
                        DetailAction.BackPress
                    )
                },

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_grey),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) { paddingValues ->
        DetailContent(
            Modifier.padding(paddingValues),
            amount = mainForm.amount
        )
    }

}

@Composable
fun DetailContent(
    modifier: Modifier,
    amount: String = ""
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = amount
        )
    }
}

//@Composable
//@Preview
//fun PreviewDetail(){
//    val vm: MainViewModel = hiltViewModel()
//    Detail(vm = vm)
//}