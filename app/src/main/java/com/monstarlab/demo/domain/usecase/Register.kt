package com.monstarlab.demo.domain.usecase

import com.monstarlab.demo.domain.respository.RegisterRepository
import javax.inject.Inject

class Register @Inject constructor(private val repo: RegisterRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repo.registerUser(email, password)
}