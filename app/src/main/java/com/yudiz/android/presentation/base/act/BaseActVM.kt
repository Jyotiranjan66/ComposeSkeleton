package com.yudiz.android.presentation.base.act

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.vm.BaseVM
import kotlinx.coroutines.flow.collect

abstract class BaseActVM<binding : ViewDataBinding, vm : BaseVM>(
    @LayoutRes private val layoutId: Int
) : BaseAct<binding>(layoutId) {

    protected abstract val vm: vm?

    protected abstract fun renderState(uiState: UiState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm?.let {
            lifecycleScope.launchWhenStarted {
                it.state().collect {
                    renderState(it)
                }
            }
        }
    }
}
