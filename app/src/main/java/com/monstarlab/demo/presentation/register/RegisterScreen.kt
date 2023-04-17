package com.monstarlab.demo.presentation.register

import android.app.Activity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.monstarlab.demo.R
import com.monstarlab.demo.common.components.ProgressBar
import com.monstarlab.demo.common.components.SimpleDialog
import com.monstarlab.demo.common.components.TopBar
import com.monstarlab.demo.presentation.register.components.RegisterContent

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    val activity = (LocalContext.current as? Activity)
    Scaffold(topBar = {
        TopBar(onClose = {
            activity?.finish()
        }, onLater = {
            activity?.finish()
        })
    }, content = { padding ->
        val loading = viewModel.isLoading.collectAsState()
        RegisterContent(
            padding = padding,
            viewModel = viewModel
        )
        SimpleDialog(
            viewModel.openDialog,
            message = stringResource(id = R.string.registration_error_mesage)
        ) {
            viewModel.closeDialog()
        }
        if (loading.value) {
            ProgressBar()
        }
    })
}
