package com.monstarlab.demo.data.common

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import kotlin.random.Random

class MockInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //If requested endpoint matched to targeted endpoint, we will return an mocked response.
        return if (chain.request().url.toUri().toString().endsWith("fake-register")) {
            val isSuccessful = Random.nextBoolean()
            if (isSuccessful) {
                val responseString = getJsonDataFromAsset(context, "mock_register_success.json")
                if (responseString != null) {
                    createDummyJsonResponse(HttpURLConnection.HTTP_OK, chain.request(), responseString)
                } else chain.proceed(chain.request())
            } else {
                val errorResponse = getJsonDataFromAsset(context, "mock_register_error.json")
                if (errorResponse != null) {
                    // Just send back a failed response
                    createDummyJsonResponse(HttpURLConnection.HTTP_FORBIDDEN, chain.request(), errorResponse)
                } else chain.proceed(chain.request())
            }
        } else {
            //Skip the interception.
            chain.proceed(chain.request())
        }
    }

    private fun createDummyJsonResponse(
        code: Int,
        request: Request,
        responseString: String,
    ): Response {
        // We need to avoid targeting an actual host (it will throw an exception) so we just mock the response
        return Response.Builder()
            .request(request)
            .code(code)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString
                    .toByteArray()
                    .toResponseBody(
                        "application/json".toMediaTypeOrNull()
                    )
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}