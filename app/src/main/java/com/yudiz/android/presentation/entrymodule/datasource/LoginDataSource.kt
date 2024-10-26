package com.yudiz.android.presentation.entrymodule.datasource

import com.yudiz.android.presentation.base.ApiCaller
import com.yudiz.core.model.UserDetailsModel
import com.yudiz.core.repo.LoginDataSourceImpl
import com.yudiz.data.api.service.EntryModuleService
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