package com.yudiz.android.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.ApiCaller.ApiResultType.*
import com.yudiz.data.api.NoInternetException
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class BaseVM : ViewModel() {

    internal val apiError = MutableSharedFlow<ApiCaller.ApiResult<Any>>()

    protected val state = MutableSharedFlow<UiState>()
    internal fun state(): SharedFlow<UiState> = state

//    fun <T> getLiveData(executable: suspend LiveDataScope<T>.() -> Unit): LiveData<T> {
//        return liveData(Dispatchers.IO, timeoutInMs = 0, block = executable)
//    }

    fun <T> asyncScope(
        dispatcher: CoroutineDispatcher = IO,
        executable: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return viewModelScope.async(dispatcher) {
            executable.invoke(this)
        }
    }

    fun <T> scope(
        dispatcher: CoroutineDispatcher = IO,
        executable: suspend CoroutineScope.() -> T
    ): Job {
        return viewModelScope.launch(dispatcher) {
            executable.invoke(this)
        }
    }

    suspend fun apiCall(autoHandle: Boolean = true, execute: suspend () -> Unit) {
        try {
            execute.invoke()
        } catch (e: NoInternetException) {
            val error = ApiCaller.ApiResult<Any>(null, NO_INTERNET, e.message)

            if (autoHandle)
                apiError.emit(error)
            else
                state.emit(UiState.ApiError(error))
        } catch (e: HttpException) {
            //todo : parse e.response() using ApiErrorUtil.kt to get error message
            val error = ApiCaller.ApiResult<Any>(
                null,
                HTTP_ERROR,
                e.message ?: "Something went wrong",
                resCode = e.code()
            )

            if (autoHandle)
                apiError.emit(error)
            else
                state.emit(UiState.ApiError(error))
        } catch (e: SocketTimeoutException) {
            //todo : parse e.response() using ApiErrorUtil.kt to get error message
            val error = ApiCaller.ApiResult<Any>(null, TIME_OUT, "Time out")

            if (autoHandle)
                apiError.emit(error)
            else
                state.emit(UiState.ApiError(error))
        } catch (e: CancellationException) {
            //todo : parse e.response() using ApiErrorUtil.kt to get error message
            val error = ApiCaller.ApiResult<Any>(null, CANCELLED, "")

            if (autoHandle)
                apiError.emit(error)
            else
                state.emit(UiState.ApiError(error))
        } catch (e: Throwable) {
            //todo : parse e.response() using ApiErrorUtil.kt to get error message
            val error =
                ApiCaller.ApiResult<Any>(null, MISCELLANEOUS, e.message ?: "Something went wrong")

            if (autoHandle)
                apiError.emit(error)
            else
                state.emit(UiState.ApiError(error))
        }
    }
}