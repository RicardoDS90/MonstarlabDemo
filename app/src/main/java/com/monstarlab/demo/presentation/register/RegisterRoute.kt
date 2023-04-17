package com.monstarlab.demo.presentation.register

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.monstarlab.demo.navigation.NavRoute

object RegisterRoute : NavRoute<RegisterViewModel> {
    override val route = "register/"

    @Composable
    override fun viewModel(): RegisterViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: RegisterViewModel) = RegisterScreen(viewModel)
}