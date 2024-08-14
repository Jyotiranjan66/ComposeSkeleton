package com.example.composeskeleton.ui.presentation.entrymodule.state

data class CommonUiState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
    val errorMessage: String? = null
)
