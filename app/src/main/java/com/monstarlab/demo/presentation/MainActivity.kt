package com.monstarlab.demo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.monstarlab.demo.navigation.NavigationComponent
import com.monstarlab.demo.presentation.register.RegisterScreen
import com.monstarlab.demo.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
//            var canPop by remember { mutableStateOf(false) }
//            DisposableEffect(navController) {
//                val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
//                    canPop = controller.previousBackStackEntry != null
//                }
//                navController.addOnDestinationChangedListener(listener)
//                onDispose {
//                    navController.removeOnDestinationChangedListener(listener)
//                }
//            }
            AppTheme {
                NavigationComponent(navController)
            }
        }
    }
}