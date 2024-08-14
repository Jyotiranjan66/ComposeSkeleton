package com.example.composeskeleton.ui.presentation.entrymodule.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeskeleton.ui.presentation.entrymodule.vm.HomeViewModel
import androidx.compose.runtime.*

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    if (uiState.isLoading) {
        // Show loading indicator
    }  else {

    }
}