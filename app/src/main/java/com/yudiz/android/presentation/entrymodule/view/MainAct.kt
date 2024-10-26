package com.yudiz.android.presentation.entrymodule.view

import android.content.Intent
import android.graphics.Color
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.*
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.smokelaboratory.whereabouts.WhereAbouts
import com.yudiz.android.Colors
import com.yudiz.android.Layouts
import com.yudiz.android.R
import com.yudiz.android.databinding.MainActBinding
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.BaseAct
import com.yudiz.android.presentation.entrymodule.vm.MainActVM
import com.yudiz.android.util.NetworkUtil
import com.yudiz.android.util.logE
import com.yudiz.data.storage.PrefUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import javax.inject.Inject

@AndroidEntryPoint
class MainAct : BaseAct<MainActBinding, MainActVM>(Layouts.act_main) {

    @Inject
    lateinit var networkUtil: NetworkUtil

    private lateinit var mediaPicker: EasyImage
    override val vm: MainActVM by viewModels()
    override val hasProgress: Boolean = true

    override fun init() {

        delayedExecutor(5000) {
            successToast("delayed toast")
        }

        binding.url =
            "https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"

        mediaPicker = EasyImage.Builder(this).allowMultiple(true).build()

        prefs.authToken = "s"

        /*freedom = freedom(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ) { isGranted, status ->
            //handle user response
        }
        freedom?.request()*/

        lifecycleScope.launch {
            try {
                val location = WhereAbouts(this@MainAct).fetchLocation()
            } catch (e: WhereAbouts.LocationPermissionException) {
                e.localizedMessage?.logE()
            } catch (e: WhereAbouts.AccuracyPermissionException) {
                e.localizedMessage?.logE()
            } catch (e: WhereAbouts.FlightModeException) {
                e.localizedMessage?.logE()
            } catch (e: WhereAbouts.PrecisionException) {
                e.localizedMessage?.logE()
            }
        }

        networkUtil.onNetworkChange(lifecycleScope) {
            "network : $it".logE()
        }

        binding.tv.text = buildSpannedString {
            bold { append("hi") }
            underline { append(" how are you ") }
            strikeThrough { append(" 2 ") }
            color(ContextCompat.getColor(this@MainAct, Colors.colorAccent)) { append(" nice ") }
            inSpans(object : ClickableSpan() {
                override fun onClick(textView: View) {
                    errorToast("asdasd")
                }

                override fun updateDrawState(ds: TextPaint) {
//                    ds.isUnderlineText = false
                }
            }) { bold { append(" !!!!!") } }
        }

        binding.tv.highlightColor = Color.TRANSPARENT
        /**
         * added for click to work
         */
        binding.tv.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getFcmToken() {
        threadExecutor(executable = {
            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                if (result.isEmpty())
                //handle error
                else {
                    prefs.fcmToken = result
                    //send in api
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.tv.id ->
                vm.login("a", "a")
//                mediaPicker.openGallery(this)
            binding.btFrag.id -> {
//                prefs.userInfo =
//                    com.skeletonkotlin.data.model.response.LoginResModel("abc", "abcdeee")
                addFrag(DemoFrag(""), R.id.frame)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaPicker.handleActivityResult(requestCode, resultCode, data, this, object :
            EasyImage.Callbacks {
            override fun onCanceled(source: MediaSource) {

            }

            override fun onImagePickerError(error: Throwable, source: MediaSource) {

            }

            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                imageFiles.size.toString().logE()
            }
        })
    }

    override fun renderState(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> showProgress()
            is UiState.ApiSuccess<*> -> {
                hideProgress()
                successToast("Success")
            }
            is UiState.ValidationError -> {
                uiState.message?.let {
                    errorToast(getString(it)) { isDismissed ->
                        Toast.makeText(this, "dismissed", Toast.LENGTH_LONG).show()
                    }
                }
            }
            is UiState.ApiError<*> -> {
            }
            is UiState.Idle -> {

            }
        }
    }
}