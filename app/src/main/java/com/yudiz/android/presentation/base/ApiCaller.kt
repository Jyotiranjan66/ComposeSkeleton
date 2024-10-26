package com.yudiz.android.presentation.base

import com.yudiz.android.presentation.base.ApiCaller.ApiResultType.SUCCESS
import com.yudiz.android.util.NetworkUtil
import com.yudiz.data.api.NoInternetException
import javax.inject.Inject

open class ApiCaller {
    @Inject
    lateinit var nwUtil: NetworkUtil

    internal suspend fun <T : Any> execute(executable: suspend () -> T): T {
        return if (nwUtil.isNetworkAvailable())
            executable.invoke()
        else
            throw NoInternetException()
    }

    data class ApiResult<T>(
        val data: T?,
        val resultType: ApiResultType = SUCCESS,
        val error: String? = null,
        val reqCode: Int = -1,
        val resCode: Int = -1
    )

    enum class ApiResultType {
        SUCCESS,
        NO_INTERNET,
        HTTP_ERROR,
        TIME_OUT,
        MISCELLANEOUS,
        CANCELLED
    }
}