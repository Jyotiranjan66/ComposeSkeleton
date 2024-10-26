package com.yudiz.android.presentation.base.vm

import androidx.lifecycle.viewModelScope
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.ApiCaller
import com.yudiz.data.api.NoInternetException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

open class BaseVMApi : BaseVM() {
    internal val apiError = MutableSharedFlow<UiState.ApiError>()

    fun <T> asyncScope(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        executable: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return viewModelScope.async(dispatcher) {
            executable.invoke(this)
        }
    }

    fun <T> scope(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        executable: suspend CoroutineScope.() -> T
    ): Job {
        return viewModelScope.launch(dispatcher) {
            executable.invoke(this)
        }
    }

    suspend fun apiCall(requestCode: Int = -1, execute: suspend () -> Unit) {
        //todo : parse e.response() using ApiErrorUtil.kt to get error message

        try {
            execute()
        } catch (e: NoInternetException) {
            apiError.emit(
                UiState.ApiError(
                    ApiCaller.ApiError.NoInternet,
                    ApiCaller.ApiResult(reqCode = requestCode)
                )
            )
        } catch (e: HttpException) {
            apiError.emit(
                UiState.ApiError(
                    ApiCaller.ApiError.HttpError(
                        e.message ?: "Something went wrong"
                    ),
                    ApiCaller.ApiResult(resCode = e.code(), reqCode = requestCode)
                )
            )
        } catch (e: SocketTimeoutException) {
            apiError.emit(
                UiState.ApiError(
                    ApiCaller.ApiError.TimeOut(
                        e.message ?: "Something went wrong"
                    ),
                    ApiCaller.ApiResult(reqCode = requestCode)
                )
            )
        } catch (e: CancellationException) {
            apiError.emit(
                UiState.ApiError(
                    ApiCaller.ApiError.Cancelled,
                    ApiCaller.ApiResult(reqCode = requestCode)
                )
            )
        } catch (e: Throwable) {
            apiError.emit(
                UiState.ApiError(
                    ApiCaller.ApiError.Miscellaneous(
                        e.message ?: "Something went wrong"
                    ),
                    ApiCaller.ApiResult(reqCode = requestCode)
                )
            )
        }
    }
}