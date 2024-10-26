package com.yudiz.android.presentation

import androidx.annotation.StringRes

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class ValidationError(@StringRes val message: Int?) : UiState()
    data class ApiSuccess<out T>(val result: T) : UiState()
    data class ApiError<out T>(val error: T?) : UiState()
}