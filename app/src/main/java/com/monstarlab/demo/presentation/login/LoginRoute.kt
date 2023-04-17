package com.monstarlab.demo.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.monstarlab.demo.navigation.NavRoute

object LoginRoute : NavRoute<LoginViewModel> {
    override val route = "login/"

    @Composable
    override fun viewModel(): LoginViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: LoginViewModel) = LoginScreen(viewModel)
}