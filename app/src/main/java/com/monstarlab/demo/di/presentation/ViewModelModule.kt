package com.monstarlab.demo.di.presentation

import com.monstarlab.demo.domain.respository.RegisterRepository
import com.monstarlab.demo.domain.usecase.Register
import com.monstarlab.demo.domain.usecase.UseCases
import com.monstarlab.demo.navigation.RouteNavigator
import com.monstarlab.demo.navigation.RouteNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideRouteNavigator(): RouteNavigator = RouteNavigatorImpl()

    @Provides
    fun provideUseCases(
        registerRepo: RegisterRepository,
    ) = UseCases(
        registerUser = Register(registerRepo)
    )
}