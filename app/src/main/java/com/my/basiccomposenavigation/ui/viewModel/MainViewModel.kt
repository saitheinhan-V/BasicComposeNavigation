package com.my.basiccomposenavigation.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.basiccomposenavigation.Accounts
import com.my.basiccomposenavigation.Bills
import com.my.basiccomposenavigation.Overview
import com.my.basiccomposenavigation.ui.model.DetailAction
import com.my.basiccomposenavigation.ui.model.MainAction
import com.my.basiccomposenavigation.ui.model.MainForm
import com.my.basiccomposenavigation.ui.utils.AppNavigator
import com.my.basiccomposenavigation.ui.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val appNavigator: AppNavigator
) : ViewModel() {

    private val vmState = MutableStateFlow(MainViewModelState())

    init {
        launchSaveState()
    }

    val mainForm = vmState
        .map(MainViewModelState::mainForm)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.mainForm()
        )


    private fun launchSaveState() {
        viewModelScope.launch {
            savedStateHandle.get<String>(key = Screen.AMOUNT)?.let {
                val form = MainForm(it)
                vmState.update { state ->
                    state.copy(
                        form = form
                    )
                }
            }
        }
    }

    fun onActionMain(action: MainAction) {
        when (action) {
            is MainAction.ChangeAmount -> {
                vmState.update { state ->
                    state.copy(
                        form = MainForm(action.amount)
                    )
                }
            }

            is MainAction.ClickAccount -> {
                appNavigator.to(
                    route = Accounts.route,
//                    popupToRoute = Accounts.route,

                )
            }

            is MainAction.ClickBill -> {
                appNavigator.to(
                    route = Bills.route,
//                    popupToRoute = Bills.route,
                )
            }

            is MainAction.ClickDetail -> {
                appNavigator.to(
                    route = Screen.DetailPage.passParam(vmState.value.form.amount),
                    popupToRoute = Screen.DETAIL,
                    inclusive = true
                )

            }
        }
    }

    fun onActionDetail(action: DetailAction) {
        when (action) {
            is DetailAction.BackPress -> {
                appNavigator.back()
            }
        }
    }
}