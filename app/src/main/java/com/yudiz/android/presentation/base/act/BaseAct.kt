package com.yudiz.android.presentation.base.act

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yudiz.android.presentation.util.ToastUtil
import javax.inject.Inject

abstract class BaseAct<binding : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    @Inject
    lateinit var toast: ToastUtil

    protected lateinit var binding: binding

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<binding>(this, layoutId).apply {
            lifecycleOwner = this@BaseAct
        }

        init()
    }

    open fun successToast(message: String, callback: ((Boolean) -> Unit)? = null) {
        toast.successSnackbar(message, binding.root, callback)
    }

    open fun errorToast(message: String, callback: ((Boolean) -> Unit)? = null) {
        toast.errorSnackbar(message, binding.root, callback)
    }
}
