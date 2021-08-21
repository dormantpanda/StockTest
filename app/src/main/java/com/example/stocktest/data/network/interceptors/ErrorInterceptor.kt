package com.example.stocktest.data.network.interceptors

import com.example.stocktest.data.network.base.ApiError
import com.google.common.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Suppress("UnstableApiUsage")
class ErrorInterceptor @Inject constructor() : Interceptor {
    val limiter : RateLimiter? = RateLimiter.create(LIMIT)

    override fun intercept(chain: Interceptor.Chain): Response {
        limiter?.acquire(1)
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) return response

        when (response.code) {
            429 -> {
                limiter?.acquire(1)
                //SystemClock.sleep(1000)
                return chain.proceed(chain.request())
                //throw ApiError.RateLimitExceeded(response.message)
            }
            else -> throw ApiError.UnknownError(response.message)
        }
    }

    companion object {
        const val LIMIT = 29.0
    }
}