package com.monstarlab.demo.presentation.register.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.monstarlab.demo.R
import com.monstarlab.demo.common.components.PrimaryButton
import com.monstarlab.demo.common.components.SecondaryButton
import com.monstarlab.demo.presentation.register.RegisterScreen
import com.monstarlab.demo.presentation.register.RegisterViewModel
import com.monstarlab.demo.theme.AppTheme
import com.monstarlab.demo.theme.SecondaryText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterContent(padding: PaddingValues, viewModel: RegisterViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 30.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val (focusRequester1, focusRequester2) = FocusRequester.createRefs()
        Text(
            modifier = Modifier.padding(bottom = 25.dp),
            text = stringResource(id = R.string.create_an_account_label),
            style = TextStyle(fontSize = 22.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold)
        )
        EmailInput(
            emailInputField = viewModel.email,
            onEmailChanged = viewModel::onEmailChanged,
            focusRequester = focusRequester1,
            focusRequesterNext = focusRequester2
        )
        PasswordInput(
            passwordInputField = viewModel.password,
            labelText = stringResource(id = R.string.password_label),
            onPasswordChanged = viewModel::onPasswordChanged,
            focusRequester = focusRequester2
        )
        Spacer(modifier = Modifier.height(20.dp))
        val isButtonEnabled = viewModel.buttonDisabledState.collectAsState()
        val controller = LocalSoftwareKeyboardController.current
        Box(modifier = Modifier.padding(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 20.dp)) {
            PrimaryButton(enabled = !isButtonEnabled.value, onClick = {
                controller?.hide()
                viewModel.onRegisterButtonClicked()
            }, title = stringResource(id = R.string.create_an_account_label))
        }
        TextButton(
            onClick = viewModel::onNavigateToLogin,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                color = SecondaryText,
                text = stringResource(id = R.string.already_have_an_account_label),
                style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Normal)
            )
        }

        Divider(color = Color.LightGray, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 40.dp)
            .width(1.dp))

        Column() {
            val content = LocalContext.current
            SecondaryButton(onClick = {
                controller?.hide()
                Toast.makeText(content, "Google Account Linking", Toast.LENGTH_SHORT).show()
                // TODO - Google Login Prompt
            }, title = stringResource(id = R.string.continue_with_google_button), icon = R.drawable.ic_google)
            Spacer(modifier = Modifier.height(15.dp))
            SecondaryButton(onClick = {
                controller?.hide()
                Toast.makeText(content, "Facebook Account Linking", Toast.LENGTH_SHORT).show()
                // TODO - Facebook Login Prompt
            }, title = stringResource(id = R.string.continue_with_facebook_button), icon = R.drawable.ic_facebook)
            Spacer(modifier = Modifier.height(15.dp))
            SecondaryButton(onClick = {
                controller?.hide()
                Toast.makeText(content, "Apple Account Linking", Toast.LENGTH_SHORT).show()
                // TODO - Apple Login Prompt (Does not really make sense here)
            }, title = stringResource(id = R.string.continue_with_apple_button), icon = R.drawable.ic_apple)
        }

    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(viewModel = hiltViewModel())
    }
}