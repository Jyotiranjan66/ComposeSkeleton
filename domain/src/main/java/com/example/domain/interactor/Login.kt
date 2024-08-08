package com.example.domain.interactor

import com.example.domain.repo.LoginRepo

class Login(private val loginRepo: LoginRepo) {
    suspend operator fun invoke(username: String, password: String) =
        loginRepo.login(username, password)
}