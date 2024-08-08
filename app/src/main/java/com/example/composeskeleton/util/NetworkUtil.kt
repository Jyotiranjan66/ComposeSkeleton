package com.example.composeskeleton.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class NetworkUtil
@Inject
constructor(@ApplicationContext var context: Context) {
    private var networkConnectivityChannel = Channel<Boolean>()
    private val networkObserver: MutableSharedFlow<Boolean> = MutableSharedFlow()

    private val appLevelJob: Job = GlobalScope.launch {
        while (true) {
            networkConnectivityChannel.receive().let {
                networkObserver.emit(it)
            }
        }
    }

    private var cm: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        cm.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(), object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    CoroutineScope(Dispatchers.IO).launch {
                        announceNetworkChange(true)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    CoroutineScope(Dispatchers.IO).launch {
                        announceNetworkChange(false)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    CoroutineScope(Dispatchers.IO).launch {
                        announceNetworkChange(false)
                    }
                }
            }
        )
    }

    fun isNetworkAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun onNetworkChange(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        listener: suspend (Boolean) -> Unit
    ) {
        networkObserver.let {
            coroutineScope.launch(dispatcher) {
                it.collect {
                    listener.invoke(it)
                }
            }
        }

        networkObserver.tryEmit(isNetworkAvailable())
    }

    private suspend fun announceNetworkChange(isAvailable: Boolean) {
        networkConnectivityChannel.send(isAvailable)
    }

    fun shutDownAnnouncer() {
        appLevelJob.cancel(CancellationException())
        networkConnectivityChannel.cancel(CancellationException())
    }

    fun isInFlightMode(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.AIRPLANE_MODE_ON,
                0
            ) != 0
        else
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0
    }

    fun changeWifiState(shouldWifiBeEnabled: Boolean) {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = shouldWifiBeEnabled
    }

}

/**
 * workaround to fix injecting [NetworkUtil] in normal classes
 *
 * in hilt, field injection is not supported for all classes
 * https://developer.android.com/training/dependency-injection/hilt-android#not-supported
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface NwService {
    fun nwUtil(): NetworkUtil
}
