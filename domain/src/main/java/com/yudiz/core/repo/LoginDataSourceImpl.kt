package com.yudiz.core.repo

import com.yudiz.core.model.UserDetailsModel

interface LoginDataSourceImpl {
    suspend fun login(username: String, password: String) : UserDetailsModel
}