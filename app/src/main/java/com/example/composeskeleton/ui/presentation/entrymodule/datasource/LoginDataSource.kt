package com.example.composeskeleton.ui.presentation.entrymodule.datasource

import com.example.composeskeleton.ui.presentation.base.ApiCaller
import com.example.data.api.service.EntryModuleService
import com.example.domain.model.UserDetailsModel
import com.example.domain.repo.LoginDataSourceImpl
import javax.inject.Inject

class LoginDataSource
@Inject
constructor(private val service: EntryModuleService) : LoginDataSourceImpl, ApiCaller() {

    override suspend fun login(username: String, password: String): UserDetailsModel =
        executeApi {
            service.login(username, password)
        }.let {
            UserDetailsModel(it.name, it.email, 10/*logic to calculate age from dob*/)
        }
}