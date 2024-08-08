package com.example.composeskeleton.ui.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarUtil(val snackbarHostState: SnackbarHostState, private val scope: CoroutineScope) {

    fun showErrorSnackbar(message: String, callback: ((Boolean) -> Unit)? = null) {
        showCustomSnackbar("error: $message", callback)
    }

    fun showSuccessSnackbar(message: String, callback: ((Boolean) -> Unit)? = null) {
        showCustomSnackbar("success: $message", callback)
    }

    private fun showCustomSnackbar(message: String, callback: ((Boolean) -> Unit)? = null) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            when (result) {
                SnackbarResult.Dismissed -> callback?.invoke(true)
                SnackbarResult.ActionPerformed -> { /* Handle if needed */ }
            }
        }
    }

    @Composable
    fun CustomSnackbarHost(modifier: Modifier = Modifier) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when {
                        snackbarData.visuals.message.contains("error", ignoreCase = true) -> Color(0xFFDD5A5A)
                        snackbarData.visuals.message.contains("success", ignoreCase = true) -> Color(0xFF87CC6C)
                        else -> MaterialTheme.colorScheme.inverseSurface
                    },
                    contentColor = Color.White
                )
            }
        )
    }

}