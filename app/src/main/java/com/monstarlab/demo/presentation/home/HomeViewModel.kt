package com.monstarlab.demo.presentation.home

import androidx.lifecycle.ViewModel
import com.monstarlab.demo.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator
) : ViewModel(), RouteNavigator by routeNavigator