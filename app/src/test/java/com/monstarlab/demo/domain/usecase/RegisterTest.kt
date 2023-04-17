package com.monstarlab.demo.domain.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.monstarlab.demo.common.mock
import com.monstarlab.demo.common.whenever
import com.monstarlab.demo.domain.common.base.Resource
import com.monstarlab.demo.domain.respository.AuthResponse
import com.monstarlab.demo.domain.respository.RegisterRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val repo: RegisterRepository = mock<RegisterRepository>()

    @InjectMocks
    lateinit var register: Register

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test should get an AuthResponse instance from repository`() = runTest {
        // GIVEN
        val mockedResponse = mock<AuthResponse>()
        whenever(repo.registerUser(anyString(), anyString())).thenReturn(mockedResponse)
        // WHEN
        val result = register("test@email.com", "12512sa2fa2")
        // THEN
        assertNotNull(result)
        assertThat(result, CoreMatchers.`is`(mockedResponse))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test register usecase should get a success resource result from repository`() = runTest {
        // GIVEN
        whenever(repo.registerUser(anyString(), anyString()))
            .thenReturn(Resource.Success(true))
        // WHEN
        val result: AuthResponse = register("test@email.com", "12512sa2fa2")
        // THEN
        assertNotNull(result)
        assert(result is Resource.Success)
        assertEquals(Resource.Success(true), result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test register usecase should get a failure resource result from repository`() = runTest {
        // GIVEN
        val mockedException = mock<Exception>()
        whenever(repo.registerUser(anyString(), anyString()))
            .thenReturn(Resource.Failure(mockedException))
        // WHEN
        val result: AuthResponse = register("test@email.com", "12512sa2fa2")
        // THEN
        assertNotNull(result)
        assert(result is Resource.Failure)
        assertEquals(Resource.Failure(mockedException), result)
        assertThat(result, CoreMatchers.`is`(Resource.Failure(mockedException)))
    }
}
