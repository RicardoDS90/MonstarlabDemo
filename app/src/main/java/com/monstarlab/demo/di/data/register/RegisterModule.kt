package com.monstarlab.demo.di.data.register

import com.monstarlab.demo.data.register.remote.api.RegisterApi
import com.monstarlab.demo.data.register.repository.RegisterRepositoryImpl
import com.monstarlab.demo.di.data.NetworkModule
import com.monstarlab.demo.domain.respository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {
    @Singleton
    @Provides
    fun provideRegisterApi(retrofit: Retrofit) : RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerApi: RegisterApi) : RegisterRepository {
        return RegisterRepositoryImpl(registerApi)
    }
}