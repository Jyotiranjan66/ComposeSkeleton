package com.yudiz.android.presentation.util

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.yudiz.android.R
import kotlinx.coroutines.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*

object AndroidUtil {

    fun rateApp(context: Context) {
        val url = "https://play.google.com/store/apps/details?id=" + context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    url.toUri()
                )
            )
        } catch (e: Exception) {
            openWebPage(context, url)
        }
    }

    fun shareApp(context: Context) {
        try {
            context.startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=" + context.packageName
                )
            })
        } catch (e: Exception) {
        }
    }

    fun composeEmail(
        context: Context,
        addresses: Array<String>,
        subject: String,
        text: String? = null
    ) {
        context.startActivity(Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }

    fun openWebPage(context: Context, url: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        } catch (e: Exception) {
        }
    }

    fun openDialPad(context: Context, number: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_DIAL).also {
                it.data = "tel:$number".toUri()
            })
        } catch (e: Exception) {
        }
    }

    fun getVersionCode(context: Context): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
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

    fun getVersionName(context: Context): String {
        try {
            return "v : ${
                context.packageManager.getPackageInfo(
                    context.packageName,
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

    fun isAppInBackground(context: Context): Boolean {
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
        val runningTasks =
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningTasks(
                1
            )
        if (runningTasks.isNotEmpty())
            return runningTasks[0].topActivity?.packageName != context.packageName
        return false
//        } else {
//            val runningTasks = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).appTasks
//            if (runningTasks.isNotEmpty())
//                return runningTasks[0].taskInfo.topActivity.packageName != packageName
//            return false
//        }
    }
}

fun Context.startActivity(
    act: Class<*>,
    bundle: Bundle? = null,
    flags: List<Int>? = null,
    shouldAnimate: Boolean = true
) {
    val intent = Intent(this, act)

    if (bundle != null)
        intent.putExtras(bundle)

    if (!flags.isNullOrEmpty())
        flags.forEach {
            intent.addFlags(it)
        }

    if (shouldAnimate)
        startActivity(
            intent, ActivityOptions.makeCustomAnimation(
                applicationContext,
                R.anim.fade_in,
                R.anim.fade_out
            ).toBundle()
        )
    else
        startActivity(intent)
}

inline fun <reified T : Fragment> AppCompatActivity.addFrag(
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    supportFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(T::class.java.name)
        add<T>(container, args = bundle)
    }
}

inline fun <reified T : Fragment> AppCompatActivity.replaceFrag(
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    supportFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(T::class.java.name)
        replace<T>(container, args = bundle)
    }
}

fun AppCompatActivity.addFrag(
    fragment: Fragment,
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    supportFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(fragment::class.java.name)
        if (bundle != null)
            fragment.arguments = bundle

        add(container, fragment)
    }
}

fun AppCompatActivity.replaceFrag(
    fragment: Fragment,
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    supportFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(fragment::class.java.name)
        if (bundle != null)
            fragment.arguments = bundle

        replace(container, fragment)
    }
}

fun AppCompatActivity.showDialogFrag(dialFrag: DialogFragment, bundle: Bundle?) {
    dialFrag.arguments = bundle
    dialFrag.show(supportFragmentManager, "")
}

inline fun <reified T : Fragment> Fragment.addFrag(
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    childFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(T::class.java.name)
        add<T>(container, args = bundle)
    }
}

fun Fragment.addFrag(
    fragment: Fragment,
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    childFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(fragment::class.java.name)
        if (bundle != null)
            fragment.arguments = bundle

        add(container, fragment)
    }
}

inline fun <reified T : Fragment> Fragment.replaceFrag(
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    childFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(T::class.java.name)
        replace<T>(container, args = bundle)
    }
}

fun Fragment.replaceFrag(
    fragment: Fragment,
    container: Int,
    addToBackStack: Boolean = false,
    shouldAnimate: Boolean = true,
    bundle: Bundle? = null
) {
    childFragmentManager.commit {
        if (shouldAnimate)
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        if (addToBackStack)
            addToBackStack(fragment::class.java.name)
        if (bundle != null)
            fragment.arguments = bundle

        replace(container, fragment)
    }
}

fun AppCompatActivity.popFrag() {
    supportFragmentManager.popBackStack()
}

fun Activity.finishAct() {
    currentFocus?.hideSoftKeyboard()
    finish()
}

fun AppCompatActivity.onContainerBackPressed() {
    if (supportFragmentManager.backStackEntryCount > 0)
        popFrag()
    else
        finishAct()
}

fun LifecycleOwner.delayedExecutor(millis: Long, executable: () -> Unit) {
    lifecycleScope.launch {
        delay(millis)
        executable.invoke()
    }
}

fun LifecycleOwner.threadExecutor(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    executable: suspend () -> Unit
): Job {
    return lifecycleScope.launch(dispatcher) {
        executable.invoke()
    }
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.obscure(){
    visibility = View.INVISIBLE
}