package com.example.composeskeleton.ui.presentation.entrymodule.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeskeleton.ui.presentation.base.BaseViewModel
import com.example.composeskeleton.ui.presentation.entrymodule.event.HomeEvent
import com.example.composeskeleton.ui.presentation.entrymodule.state.CommonUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(

) : BaseViewModel() {
    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.value = CommonUiState(isLoading = true)
            try {
                val items = listOf<String>()
                _uiState.value = CommonUiState(items = items)
            } catch (e: Exception) {
                _uiState.value = CommonUiState(errorMessage = e.message)
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> loadItems()
            else ->{}
        }
    }
}