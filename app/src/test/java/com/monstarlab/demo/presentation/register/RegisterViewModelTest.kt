package com.monstarlab.demo.presentation.register

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.monstarlab.demo.common.mock
import com.monstarlab.demo.common.model.InputField
import com.monstarlab.demo.domain.common.base.Resource
import com.monstarlab.demo.domain.usecase.Register
import com.monstarlab.demo.domain.usecase.UseCases
import com.monstarlab.demo.navigation.RouteNavigator
import com.monstarlab.demo.presentation.login.LoginRoute
import com.monstarlab.demo.util.MainDispatcherRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.doReturn

@RunWith(JUnit4::class)
class RegisterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()
    private val routeNavigator: RouteNavigator = mock<RouteNavigator>()
    private val registerUseCase: Register = mock<Register>()
    private val useCases: UseCases = UseCases(registerUser = registerUseCase)

    lateinit var mRegisterViewModel: RegisterViewModel

    @Before
    fun setUp() {
        mRegisterViewModel = RegisterViewModel(routeNavigator, useCases)
    }

    @Test
    fun `test register viewModel initial state`() {
        // GIVEN
        // WHEN
        //THEN
        assertEquals(mRegisterViewModel.registerResponse, Resource.Success(false))
        assertEquals(mRegisterViewModel.email.value, InputField())
        assertEquals(mRegisterViewModel.password.value, InputField())
        assertEquals(mRegisterViewModel.buttonDisabledState.value, true)
        assertEquals(mRegisterViewModel.isLoading.value, false)
        assertEquals(mRegisterViewModel.openDialog, false)
    }

    @Test
    fun `test onEmailChanged error, empty email (cleared) input field set to error with empty error message`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onEmailChanged("")
        //THEN
        val emailState = mRegisterViewModel.email.value
        assertEquals("", emailState.input)
        assertEquals(true, emailState.isError)
        assertEquals("It cannot be empty!", emailState.errorMessage)
    }

    @Test
    fun `test onEmailChanged error, invalid email input field set to error with empty invalid email message`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onEmailChanged("test")
        //THEN
        val emailState1 = mRegisterViewModel.email.value
        assertEquals("test", emailState1.input)
        assertEquals(true, emailState1.isError)
        assertEquals("Enter valid Email address!", emailState1.errorMessage)
        // WHEN (2)
        mRegisterViewModel.onEmailChanged("test@test")
        //THEN (2)
        val emailState2 = mRegisterViewModel.email.value
        assertEquals("test@test", emailState2.input)
        assertEquals(true, emailState2.isError)
        assertEquals("Enter valid Email address!", emailState2.errorMessage)
    }

    @Test
    fun `test onEmailChanged success, valid email input field set email with no errors`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onEmailChanged("test@test.com")
        //THEN
        val emailState = mRegisterViewModel.email.value
        assertEquals("test@test.com", emailState.input)
        assertEquals(false, emailState.isError)
        assertEquals("", emailState.errorMessage)
    }

    @Test
    fun `test onPasswordChanged error, empty password (cleared) input field set to error with empty error message`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onPasswordChanged("")
        //THEN
        val passwordState = mRegisterViewModel.password.value
        assertEquals("", passwordState.input)
        assertEquals(true, passwordState.isError)
        assertEquals("It cannot be empty!", passwordState.errorMessage)
    }

    @Test
    fun `test onPasswordChanged error, password less than minimum required input field set to error with length error message`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onPasswordChanged("123456")
        //THEN
        val passwordState = mRegisterViewModel.password.value
        assertEquals("123456", passwordState.input)
        assertEquals(true, passwordState.isError)
        assertEquals(
            "Password length needs to be at least 8 characters long!",
            passwordState.errorMessage
        )
    }

    @Test
    fun `test onPasswordChanged error, password using only numbers input field set to error with invalid password`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onPasswordChanged("12345678")
        //THEN
        val passwordState = mRegisterViewModel.password.value
        assertEquals("12345678", passwordState.input)
        assertEquals(true, passwordState.isError)
        assertEquals(
            "Must contain at least one number and one letter, and at least 8 or more characters!",
            passwordState.errorMessage
        )
    }

    @Test
    fun `test onPasswordChanged, password using only letters input field set to error with invalid password`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onPasswordChanged("abcdefgh")
        //THEN
        val passwordState = mRegisterViewModel.password.value
        assertEquals("abcdefgh", passwordState.input)
        assertEquals(true, passwordState.isError)
        assertEquals(
            "Must contain at least one number and one letter, and at least 8 or more characters!",
            passwordState.errorMessage
        )
    }

    @Test
    fun `test onPasswordChanged success, valid password input field set password with no errors`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onPasswordChanged("12345abcdef")
        //THEN
        val passwordState = mRegisterViewModel.password.value
        assertEquals("12345abcdef", passwordState.input)
        assertEquals(false, passwordState.isError)
        assertEquals("", passwordState.errorMessage)
        assertEquals("", passwordState.errorMessage)
    }

    @Test
    fun `test onNavigateToLogin, ensure navigateToRoute to LoginRoute is called`() {
        // GIVEN
        // WHEN
        mRegisterViewModel.onNavigateToLogin()
        // THEN
        Mockito.verify(routeNavigator, Mockito.times(1)).navigateToRoute(LoginRoute.route)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test onRegisterButtonClicked, should get a successful response`() = runTest {
        // GIVEN
        doReturn(Resource.Success(true)).`when`(useCases.registerUser)
            .invoke(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        // WHEN
        mRegisterViewModel.onEmailChanged("test@success.com")
        mRegisterViewModel.onPasswordChanged("12345abcdef")
        mRegisterViewModel.onRegisterButtonClicked()
        advanceUntilIdle() // Let the coroutine complete and changes propagate
        // Then
        val response = mRegisterViewModel.registerResponse
        assertNotNull(response)
        assert(response is Resource.Success)
        assertEquals(Resource.Success(true), response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test onRegisterButtonClicked, should get a failure response`() = runTest {
        // GIVEN
        val mockedException = mock<Exception>()
        doReturn(Resource.Failure(mockedException)).`when`(useCases.registerUser)
            .invoke(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        // WHEN
        mRegisterViewModel.onEmailChanged("test@failure.com")
        mRegisterViewModel.onPasswordChanged("12345abcdef")
        mRegisterViewModel.onRegisterButtonClicked()
        advanceUntilIdle() // Let the coroutine complete and changes propagate
        // Then
        val response = mRegisterViewModel.registerResponse
        assertNotNull(response)
        assert(response is Resource.Failure)
        assertEquals(Resource.Failure(mockedException), response)
        assertThat(response, CoreMatchers.`is`(Resource.Failure(mockedException)))
    }
}