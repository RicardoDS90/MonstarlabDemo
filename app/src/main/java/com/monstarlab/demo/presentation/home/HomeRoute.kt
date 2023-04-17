package com.monstarlab.demo.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.monstarlab.demo.navigation.NavRoute

object HomeRoute : NavRoute<HomeViewModel> {
    override val route = "home/"

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: HomeViewModel) = HomeScreen(viewModel)
}