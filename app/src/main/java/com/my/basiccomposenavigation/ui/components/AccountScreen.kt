package com.my.basiccomposenavigation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(){
    val datePickerState = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "This is account")
        Button(
            onClick = {
                openDialog.value = true
            }
        ){
            Text(text = "Open Date Picker")
        }
        if(openDialog.value){
            DatePickerDialog(state = datePickerState, openDialog = openDialog)
        }

        if (datePickerState.selectedDateMillis != 0L) {
            val date = datePickerState.selectedDateMillis?.let { Date(it) }
            val format = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            if (date != null) {
                Text(
                    text = format.format(date)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    state: DatePickerState,
    openDialog: MutableState<Boolean>
){
    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("CANCEL")
            }
        },
        colors = DatePickerDefaults.colors()
    ) {
        DatePicker(
            state = state
        )
    }
}