package com.example.data.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

inline fun <reified B> String.convertToModel(): B {
    return Json { encodeDefaults = true }.decodeFromString(this)
}