package com.yudiz.android.presentation.util

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.yudiz.android.util.Styles
import com.yudiz.android.presentation.util.LanguageUtil.throttleFirst

object UiUtil {

    inline fun <reified T : ViewDataBinding> createDialog(
        context: Context,
        @LayoutRes layoutId: Int,
        isCancelable: Boolean = false
    ): Pair<Dialog, T>? {
        try {
            Dialog(context, Styles.DialogStyle).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                DataBindingUtil.inflate<T>(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    layoutId,
                    null,
                    false
                ).let {
                    setContentView(it.root)

                    setCancelable(isCancelable)
                    if (isCancelable) {
                        it.root.setOnClickListener {
                            dismiss()
                        }
                    }

                    return Pair(this, it)
                }
            }
        } catch (e: Exception) {
            return null
        }
    }

    fun getResId(context: Context, name: String): Int {
        return context.resources.getIdentifier(
            name,
            "id",
            context.packageName
        )
    }
}

fun View.unFocusParent(onFocus: Boolean = false) {
    setOnTouchListener { v, _ ->

        v.parent.requestDisallowInterceptTouchEvent(
            if (onFocus)
                v.hasFocus()
            else
                true
        )

        false
    }
}

fun View.applyTint(color: Int, isAndroidRes: Boolean = false) {
    val colorFilter = if (isAndroidRes) ContextCompat.getColor(context, color) else color
    val mode = android.graphics.PorterDuff.Mode.SRC_IN

    if (this is ImageView)
        setColorFilter(
            colorFilter,
            mode
        )
    else
        background.colorFilter = PorterDuffColorFilter(colorFilter, mode)
}

fun View.clearTint() {
    if (this is ImageView)
        colorFilter = null
    else
        background.colorFilter = null
}

fun TextView.textColor(context: Context, textColor: Int) {
    setTextColor(ContextCompat.getColor(context, textColor))
}

fun View.hideSoftKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun View.showSoftKeyboard() {
    if (requestFocus())
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            this,
            InputMethodManager.SHOW_IMPLICIT
        )
}

fun View.onClickListener(lifecycleOwner: LifecycleOwner, listener: (View) -> Unit) {
    val throttle = throttleFirst<Unit>(
        skipMs = 1000L,
        scope = lifecycleOwner.lifecycleScope
    ) {
        listener.invoke(this)
    }

    this.setOnClickListener {
        throttle(Unit)
    }
}



