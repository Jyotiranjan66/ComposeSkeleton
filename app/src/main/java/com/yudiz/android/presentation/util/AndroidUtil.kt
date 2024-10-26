package com.yudiz.android.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*

object AndroidUtil {

    fun rateApp(act: Activity) {
        val url = "https://play.google.com/store/apps/details?id=" + act.packageName
        try {
            act.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    url.toUri()
                )
            )
        } catch (e: Exception) {
            openWebPage(act, url)
        }
    }

    fun shareApp(act: Activity) {
        try {
            act.startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=" + act.packageName
                )
            })
        } catch (e: Exception) {
        }
    }

    fun composeEmail(
        act: Activity,
        addresses: Array<String>,
        subject: String,
        text: String? = null
    ) {
        act.startActivity(Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }

    fun openWebPage(context: Activity, url: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        } catch (e: Exception) {
        }
    }

    fun openDialPad(context: Activity, number: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_DIAL).also {
                it.data = "tel:$number".toUri()
            })
        } catch (e: Exception) {
        }
    }

    fun getVersionCode(con: Context): String {
        try {
            val packageInfo = con.packageManager.getPackageInfo(
                con.packageName,
                0
            )

            return "v : ${
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    packageInfo.longVersionCode
                else
                    packageInfo.versionCode.toLong()
            }"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getVersionName(con: Context): String {
        try {
            return "v : ${
                con.packageManager.getPackageInfo(
                    con.packageName,
                    0
                ).versionName
            }"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    fun hideStatusBar(act: Activity) {
        act.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun getDeviceWidth(act: Activity): Int {
        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getDeviceHeight(act: Activity): Int {
        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getHashKey(context: Context): String? {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.DEFAULT)
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

        return null
    }

    fun getInstalledLanguages(): LocaleListCompat {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration)
    }

    fun arePlayServicesAvailable(act: Activity, reqCode: Int): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(act)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status))
                googleApiAvailability.getErrorDialog(act, status, reqCode)?.show()
            return false
        }
        return true
    }

    fun isPackageInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(0)
        for (packageInfo in packages)
            if (packageInfo.packageName == packageName) return true
        return false
    }
}






