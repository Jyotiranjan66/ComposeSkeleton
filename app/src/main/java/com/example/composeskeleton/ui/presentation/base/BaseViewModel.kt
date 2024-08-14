package com.example.composeskeleton.ui.presentation.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composeskeleton.ui.presentation.entrymodule.state.CommonUiState

open class BaseViewModel : ViewModel() {
    protected val _uiState = mutableStateOf(CommonUiState())
    val uiState: State<CommonUiState> = _uiState
}