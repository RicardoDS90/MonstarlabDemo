package com.monstarlab.demo.data.register.repository

import com.monstarlab.demo.data.register.remote.api.RegisterApi
import com.monstarlab.demo.domain.common.base.Resource.Failure
import com.monstarlab.demo.domain.common.base.Resource.Success
import com.monstarlab.demo.domain.respository.AuthResponse
import com.monstarlab.demo.domain.respository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi) :
    RegisterRepository {
    override suspend fun registerUser(email: String, password: String): AuthResponse =
        // For future improvements, move this to an extension to parse exceptions
        withContext(Dispatchers.IO) {
            try {
                registerApi.mockRegister(email, password)
                // Here we will save the auth token to prefs (for auth persistence)
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
}