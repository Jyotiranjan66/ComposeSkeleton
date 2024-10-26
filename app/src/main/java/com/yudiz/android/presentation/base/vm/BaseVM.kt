package com.yudiz.android.presentation.base.vm

import androidx.lifecycle.ViewModel
import com.yudiz.android.presentation.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

open class BaseVM : ViewModel() {
    protected val state = MutableSharedFlow<UiState>()
    internal fun state(): SharedFlow<UiState> = state
}