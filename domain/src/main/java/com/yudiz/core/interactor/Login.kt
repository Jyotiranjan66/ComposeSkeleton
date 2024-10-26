package com.yudiz.core.interactor

import com.yudiz.core.repo.LoginRepo

class Login(private val loginRepo: LoginRepo) {
    suspend operator fun invoke(username: String, password: String) =
        loginRepo.login(username, password)
}