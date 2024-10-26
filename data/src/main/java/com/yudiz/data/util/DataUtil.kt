package com.yudiz.data.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DataUtil {
}

inline fun <reified B> String.convertToModel(): B {
    return Json { encodeDefaults = true }.decodeFromString(this)
}