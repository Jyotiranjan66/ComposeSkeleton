package com.example.composeskeleton.ui.presentation.base

import com.example.composeskeleton.util.NetworkUtil
import com.example.data.api.NoInternetException
import javax.inject.Inject

open class ApiCaller {
    @Inject
    lateinit var nwUtil: NetworkUtil

    internal suspend fun <T : Any> executeApi(executable: suspend () -> T): T {
        return if (nwUtil.isNetworkAvailable())
            executable.invoke()
        else
            throw NoInternetException()
    }

    data class ApiResult<T>(
        val data: T? = null,
        val reqCode: Int = -1,
        val resCode: Int = -1
    )

    sealed class ApiError {
        object NoInternet : ApiError()
        data class HttpError(val message: String) : ApiError()
        data class TimeOut(val message: String) : ApiError()
        data class Miscellaneous(val message: String) : ApiError()
        object Cancelled : ApiError()
    }
}