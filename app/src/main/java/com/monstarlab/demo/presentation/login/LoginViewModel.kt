package com.monstarlab.demo.presentation.login

import androidx.lifecycle.ViewModel
import com.monstarlab.demo.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator
) : ViewModel(), RouteNavigator by routeNavigator