package com.monstarlab.demo.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstarlab.demo.common.PasswordLimits
import com.monstarlab.demo.common.model.InputField
import com.monstarlab.demo.domain.common.base.Resource
import com.monstarlab.demo.domain.respository.AuthResponse
import com.monstarlab.demo.domain.usecase.UseCases
import com.monstarlab.demo.navigation.RouteNavigator
import com.monstarlab.demo.presentation.home.HomeRoute
import com.monstarlab.demo.presentation.login.LoginRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val useCases: UseCases
) : ViewModel(), RouteNavigator by routeNavigator {
    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^" + // start of string
                    "(?=.*\\d)" + // a digit must occur at least once
                    "(?=.*[a-zA-Z])" + // a lower case OR upper case letter must occur at least once
                    "(?=\\S+$)" +  // no white spaces
                    ".{${PasswordLimits.MIN},}" +  // at least 8 characters
                    "$" // end of string
        )
    }

    var registerResponse by mutableStateOf<AuthResponse>(Resource.Success(false))
        private set

    // State of the email.
    var email = MutableStateFlow(InputField())
        private set

    // State of the password.
    var password = MutableStateFlow(InputField())
        private set

    // This is to ensure the default state (Button) is set to disabled
    var buttonDisabledState = MutableStateFlow(true)
        private set

    var isLoading = MutableStateFlow(false)
        private set

    /**
     * Stores if the last register attempt failed. This is cleared in any modification. Used to prevent
     * sending request with data that already failed.
     */
    private var registerFailed = false

    // This manages the error state (showing error message)
    var openDialog by mutableStateOf(false)
        private set

    init {
        addRegisterResponseObserver()
    }

    private fun addRegisterResponseObserver() {
        snapshotFlow { registerResponse }
            .onEach {
                System.err.println(">>> it: $it")
                when (val response = it) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            isLoading.value = false
                            if (response.data) {
                                onNavigateToHome()
                            }
                        }
                    }
                    is Resource.Failure -> {
                        withContext(Dispatchers.Main) {
                            isLoading.value = false
                            openDialog()
                        }
                    }
                }
                buttonDisabledState.value = isButtonDisabledState()
            }.launchIn(viewModelScope)
    }

    fun onRegisterButtonClicked() = viewModelScope.launch {
        registerResponse = Resource.Loading
        registerResponse = useCases.registerUser(email.value.input, password.value.input)
    }

    fun onEmailChanged(newEmailValue: String) {
        if (newEmailValue.isBlank()) {
            val message = "It cannot be empty!"
            email.value =
                email.value.copy(input = newEmailValue, isError = true, errorMessage = message)
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(newEmailValue).matches()) {
            val message = "Enter valid Email address!"
            email.value =
                email.value.copy(input = newEmailValue, isError = true, errorMessage = message)
        } else {
            email.value = email.value.copy(input = newEmailValue, isError = false)
        }
        registerFailed = false //clear fail
        buttonDisabledState.value = isButtonDisabledState()
    }

    fun onPasswordChanged(newPasswordValue: String) {
        when {
            newPasswordValue.isBlank() -> {
                val message = "It cannot be empty!"
                password.value = password.value.copy(
                    input = newPasswordValue,
                    isError = true,
                    errorMessage = message
                )
            }
            newPasswordValue.length < PasswordLimits.MIN -> {
                val message =
                    "Password length needs to be at least 8 characters long!"
                password.value = password.value.copy(
                    input = newPasswordValue,
                    isError = true,
                    errorMessage = message
                )
            }
            !PASSWORD_PATTERN.matcher(newPasswordValue).matches() -> {
                val message =
                    "Must contain at least one number and one letter, and at least 8 or more characters!"
                password.value = password.value.copy(
                    input = newPasswordValue,
                    isError = true,
                    errorMessage = message
                )
            }
            else -> {
                password.value = password.value.copy(input = newPasswordValue, isError = false)
            }
        }
        registerFailed = false //clear fail
        buttonDisabledState.value = isButtonDisabledState()
    }

    fun onNavigateToLogin() {
        navigateToRoute(LoginRoute.route)
    }

    private fun onNavigateToHome() {
        navigateToRoute(HomeRoute.route, true)
    }

    private fun isButtonDisabledState(): Boolean {
        return errorsInInputPresent() || emptyRequiredInputsPresent() || isLoading.value
    }

    private fun errorsInInputPresent(): Boolean {
        return registerFailed || email.value.isError || password.value.isError || emptyRequiredInputsPresent()
    }

    private fun emptyRequiredInputsPresent(): Boolean {
        return email.value.input.isBlank() || password.value.input.isBlank()
    }

    private fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }

}