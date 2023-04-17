package com.monstarlab.demo.data.register.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.monstarlab.demo.common.mock
import com.monstarlab.demo.common.whenever
import com.monstarlab.demo.data.register.remote.api.RegisterApi
import com.monstarlab.demo.data.register.remote.dto.RegisterUserResponse
import com.monstarlab.demo.domain.common.base.Resource
import com.monstarlab.demo.domain.respository.AuthResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegisterRepositoryTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val api: RegisterApi = mock<RegisterApi>()

    @InjectMocks
    lateinit var repo: RegisterRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test register repository should get a success response instance from api`() = runTest {
        // GIVEN
        val mockedApiResponse = mock<RegisterUserResponse>()
        whenever(
            api.mockRegister(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(mockedApiResponse)
        // WHEN
        val response: AuthResponse = repo.registerUser("test@email.com", "12512sa2fa2")
        // THEN
        assertNotNull(response)
        assert(response is Resource.Success)
        assertEquals((response as Resource.Success).data, true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = Exception::class)
    fun `test register repository should get a failure response instance from api`() = runTest {
        // GIVEN
        val mockedException = mock<Exception>()
        whenever(
            api.mockRegister(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenThrow(mockedException)
        // WHEN
        val response: AuthResponse = repo.registerUser("test@email.com", "12512sa2fa2")
        // THEN
        assertNotNull(response)
        assert(response is Resource.Failure)
        assertEquals(Resource.Failure(mockedException), response)
        MatcherAssert.assertThat(response, CoreMatchers.`is`(Resource.Failure(mockedException)))
    }
}