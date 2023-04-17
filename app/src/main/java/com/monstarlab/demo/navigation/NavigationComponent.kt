package com.monstarlab.demo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.monstarlab.demo.presentation.home.HomeRoute
import com.monstarlab.demo.presentation.login.LoginRoute
import com.monstarlab.demo.presentation.register.RegisterRoute

@Composable
fun NavigationComponent(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = RegisterRoute.route
    ) {
        RegisterRoute.composable(this, navHostController)
        LoginRoute.composable(this, navHostController)
        HomeRoute.composable(this, navHostController)
    }
}