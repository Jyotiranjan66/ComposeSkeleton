package com.yudiz.android.presentation.base.dialfrag

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.vm.BaseVM
import kotlinx.coroutines.flow.collect

abstract class BaseDialFragVM<binding : ViewDataBinding, vm : BaseVM>(
    @LayoutRes private val layoutId: Int
) : BaseDialFrag<binding>(layoutId) {

    protected abstract val vm: vm?

    protected abstract fun renderState(uiState: UiState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm?.let {
            lifecycleScope.launchWhenStarted {
                it.state().collect {
                    renderState(it)
                }
            }
        }
    }
}
