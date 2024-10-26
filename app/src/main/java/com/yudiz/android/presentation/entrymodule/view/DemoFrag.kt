package com.yudiz.android.presentation.entrymodule.view

import android.Manifest
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.smokelaboratory.freedom.freedom
import com.yudiz.android.BR
import com.yudiz.android.Layouts
import com.yudiz.android.databinding.DemoFragBinding
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.BaseFrag
import com.yudiz.android.presentation.base.BaseVM
import com.yudiz.android.presentation.base.rv.BaseRvBindingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoFrag(val text: String) : BaseFrag<DemoFragBinding, BaseVM>(Layouts.frag_demo) {

    private lateinit var backPressCallback: OnBackPressedCallback
    private val backPressDispatcher by lazy { requireActivity().onBackPressedDispatcher }

    override val vm: BaseVM? = null

    override fun init() {
        backPressCallback = backPressDispatcher.addCallback(this) {
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

            backPressCallback.isEnabled = false
            /**
             * to call parent's back press => [backPressDispatcher].onBackPressed()
             * callback in child should be disabled before that, else it enters into infinite loop
             */
        }

        binding.vp.adapter =
            BaseRvBindingAdapter(
                Layouts.row_demo,
                mutableListOf("1", "2", "3", "4", "5", "6"),
                br = BR.content
            )

        //permission request
        freedom = freedom(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ) { isGranted, status ->
            //handle user response
        }

        freedom = freedom(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            {
                title = "Permission needed"
                negativeButtonText = "Not Now"
            }
        ) { isGranted, status ->
            //handle user response
        }
    }

    override fun renderState(uiState: UiState) {

    }

    override val hasProgress: Boolean
        get() = false
}