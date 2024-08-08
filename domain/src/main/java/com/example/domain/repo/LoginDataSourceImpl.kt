package com.example.domain.repo

import com.example.domain.model.UserDetailsModel

interface LoginDataSourceImpl {
    suspend fun login(username: String, password: String) : UserDetailsModel
}