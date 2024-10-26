package com.yudiz.android.presentation.base.dialfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.yudiz.android.presentation.util.ToastUtil
import javax.inject.Inject

abstract class BaseDialFrag<binding : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : DialogFragment() {

    @Inject
    lateinit var toast: ToastUtil

    protected lateinit var binding: binding

    protected abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<binding>(inflater, layoutId, container, false).apply {
            lifecycleOwner = this@BaseDialFrag
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    open fun successToast(message: String, callback: ((Boolean) -> Unit)? = null) {
        toast.successSnackbar(message, binding.root, callback)
    }

    open fun errorToast(message: String, callback: ((Boolean) -> Unit)? = null) {
        toast.errorSnackbar(message, binding.root, callback)
    }
}
