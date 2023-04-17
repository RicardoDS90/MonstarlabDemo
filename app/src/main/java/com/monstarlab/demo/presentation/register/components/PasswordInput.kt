package com.monstarlab.demo.presentation.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.monstarlab.demo.common.model.InputField
import kotlinx.coroutines.flow.StateFlow

/**
 * Creates a password field. Validates password.
 * @param passwordInputField State flow of the [InputField] that belongs to this password.
 * @param labelText Displayed label text, if there is no error.
 * @param onPasswordChanged Called with the new value if the user types.
 * @param focusRequester [FocusRequester] of this field.
 * @param focusRequesterNext [FocusRequester] of the next field, or null if there is none.
 */
@ExperimentalComposeUiApi
@Composable
fun PasswordInput(
    passwordInputField: StateFlow<InputField>,
    labelText: String,
    onPasswordChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    focusRequesterNext: FocusRequester? = null
) {
    val password = passwordInputField.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = password.value.input,
        onValueChange = { onPasswordChanged(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        label = {
            val text = if (password.value.isError) password.value.errorMessage else labelText
            Text(text = text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if (focusRequesterNext != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() },
            onNext = { focusRequesterNext?.requestFocus() }
        ),
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 8.dp)
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        isError = password.value.isError,
        maxLines = 1
    )
}