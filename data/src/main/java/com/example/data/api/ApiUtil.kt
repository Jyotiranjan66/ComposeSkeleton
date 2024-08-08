package com.example.data.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun File.getRequestBodyFile(parameter: String): MultipartBody.Part = MultipartBody.Part.createFormData(
    parameter,
    name,
    asRequestBody("application_view/json".toMediaTypeOrNull())
)

fun String.getRequestBodyString() = toRequestBody("application_view/json".toMediaTypeOrNull())