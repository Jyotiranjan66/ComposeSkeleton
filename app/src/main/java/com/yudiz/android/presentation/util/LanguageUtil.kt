package com.yudiz.android.presentation.util

import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.util.*

object LanguageUtil {


    fun <T> throttleLatest(
        intervalMs: Long = 300L,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        executable: (T) -> Unit
    ): (T) -> Unit {
        var job: Job? = null
        var latestParam: T
        return { param: T ->
            latestParam = param
            if (job?.isCompleted != false)
                job = scope.launch(dispatcher) {
                    delay(intervalMs)
                    executable(latestParam)
                }
        }
    }

    /**
     * Constructs a function that processes input data and passes the first data to [executable] and skips all new data for the next [skipMs].
     */
    fun <T> throttleFirst(
        skipMs: Long = 300L,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        executable: (T) -> Unit
    ): (T) -> Unit {
        var job: Job? = null
        return { param: T ->
            if (job?.isCompleted != false)
                job = scope.launch(dispatcher) {
                    executable(param)
                    delay(skipMs)
                }
        }
    }
}


fun Double.format(digitsAfterDecimal: Int): String {
    return DecimalFormat("0.${"#".repeat(digitsAfterDecimal)}").format(this)    //#. instead of 0. if we need output as 1.00
}

fun String.firstCapital(): String {
    return this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1, this.length)
}
