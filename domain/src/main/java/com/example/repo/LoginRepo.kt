package com.example.domain.repo

import com.example.domain.repo.LoginDataSourceImpl
class LoginRepo(private val loginDataSourceImpl: LoginDataSourceImpl) {
    suspend fun login(username: String, password: String) =
        loginDataSourceImpl.login(username, password)
}