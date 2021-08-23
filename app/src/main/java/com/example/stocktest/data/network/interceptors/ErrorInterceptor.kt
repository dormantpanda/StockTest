package com.example.stocktest.data.network.interceptors

import com.example.stocktest.data.network.base.ApiError
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Suppress("UnstableApiUsage")
class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) return response

        when (response.code) {
            429 -> {
                throw ApiError.RateLimitExceeded(response.message)
            }
            else -> throw ApiError.UnknownError(response.message)
        }
    }
}