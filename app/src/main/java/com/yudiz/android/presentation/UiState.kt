package com.yudiz.android.presentation

import androidx.annotation.StringRes
import com.yudiz.android.presentation.base.ApiCaller

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class ValidationError(@StringRes val message: Int?) : UiState()
    data class ApiSuccess<out T>(val result: T, val requestCode: Int = -1) : UiState()
    data class ApiError(
        val error: ApiCaller.ApiError,
        val data: ApiCaller.ApiResult<Any>?
    ) : UiState()
}