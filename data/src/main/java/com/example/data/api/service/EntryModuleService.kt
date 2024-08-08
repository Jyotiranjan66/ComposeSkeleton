package com.example.data.api.service

import com.example.data.api.ApiConstants.EndUrl.LOGIN
import com.example.data.api.data.response.LoginResModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EntryModuleService {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field("username") uName: String,
        @Field("password") pass: String
    ): LoginResModel

}