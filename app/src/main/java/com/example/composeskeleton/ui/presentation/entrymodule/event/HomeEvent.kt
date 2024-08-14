package com.example.composeskeleton.ui.presentation.entrymodule.event

sealed class HomeEvent {
    data object Refresh : HomeEvent()
    data class SelectItem(val itemId: String) : HomeEvent()
}