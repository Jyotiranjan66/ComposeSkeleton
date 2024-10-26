package com.yudiz.android.presentation.base.dialfrag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.yudiz.android.presentation.Progressable
import com.yudiz.android.presentation.base.ApiCaller
import com.yudiz.android.presentation.base.vm.BaseVMApi
import com.yudiz.android.presentation.entrymodule.view.MainAct
import com.yudiz.android.presentation.util.startActivity
import com.yudiz.data.api.ApiConstants
import com.yudiz.data.storage.PrefUtil
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

abstract class BaseDialFragApi<binding : ViewDataBinding, vm : BaseVMApi>(
    @LayoutRes private val layoutId: Int
) : BaseDialFragVM<binding, vm>(layoutId) {

    @Inject
    lateinit var prefs: PrefUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm?.let {
            lifecycleScope.launchWhenStarted {
                it.apiError.collect {
                    hideProgress()

                    when (it.error) {
                        ApiCaller.ApiError.Cancelled -> {}
                        is ApiCaller.ApiError.HttpError -> {
                            if (it.data?.resCode == ApiConstants.ResponseCode.UNAUTHORIZED_CODE)
                                logout()
                            else
                                errorToast(it.error.message)
                        }

                        is ApiCaller.ApiError.Miscellaneous -> errorToast(it.error.message)
                        ApiCaller.ApiError.NoInternet -> {}
                        is ApiCaller.ApiError.TimeOut -> errorToast(it.error.message)
                    }
                }
            }
        }
    }

    private fun logout() {
        prefs.clearPrefs()

        context?.startActivity(
            act = MainAct::class.java,
            flags = listOf(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        activity?.finish()
    }

    private fun hideProgress() {
        if (this is Progressable)
            hideProgress()
    }
}
