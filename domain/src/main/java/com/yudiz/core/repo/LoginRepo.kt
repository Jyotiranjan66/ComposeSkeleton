package com.yudiz.core.repo

class LoginRepo(private val loginDataSourceImpl: LoginDataSourceImpl) {
    suspend fun login(username: String, password: String) =
        loginDataSourceImpl.login(username, password)
}