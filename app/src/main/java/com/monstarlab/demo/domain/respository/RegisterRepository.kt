package com.monstarlab.demo.domain.respository

import com.monstarlab.demo.domain.common.base.Resource

typealias AuthResponse = Resource<Boolean>

interface RegisterRepository {
    suspend fun registerUser(email: String, password: String): AuthResponse
}