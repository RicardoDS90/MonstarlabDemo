package com.monstarlab.demo.di.data

import android.content.Context
import com.monstarlab.demo.data.common.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_BASE_URL = "https://rommansabbir/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(API_BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        mockInterceptor: MockInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            retryOnConnectionFailure(true)
            // (Handle dummy responses)
            addInterceptor(mockInterceptor)
        }.build()
    }

    @Provides
    fun provideMockInterceptor(@ApplicationContext appContext: Context): MockInterceptor {
        return MockInterceptor(appContext)
    }
}