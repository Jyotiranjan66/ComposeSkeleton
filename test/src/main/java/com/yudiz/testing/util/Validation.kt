package com.yudiz.testing.util

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

fun String.isEmailValid() =
    Pattern.compile(PatternsCompat.EMAIL_ADDRESS.pattern(), Pattern.CASE_INSENSITIVE).matcher(this)
        .matches()

fun String.isPasswordValid() = length >= 5