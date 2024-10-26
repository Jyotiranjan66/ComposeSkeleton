package com.yudiz.android.presentation.entrymodule.vm

import com.yudiz.android.Interactor
import com.yudiz.android.presentation.UiState
import com.yudiz.android.presentation.base.vm.BaseVMApi
import com.yudiz.android.util.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActVM
@Inject
constructor(private val interactor: Interactor) : BaseVMApi() {

    val emailData = MutableStateFlow("abc")

    fun login(email: String, password: String) {
        scope {
            if (email.isEmpty()) {
                state.emit(UiState.ValidationError(Strings.app_name))
                return@scope
            }
            state.emit(UiState.Loading)

            apiCall {
                state.emit(UiState.ApiSuccess(interactor.login(email, password)))
            }
        }
    }

    /*fun insertData() {
        scope {
            repo.insertData()
        }
    }*/
}