package com.example.stocktest.data.network.interceptors

import com.example.stocktest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val apiKey = BuildConfig.API_KEY
        request =
            request.newBuilder().addHeader("X-Finnhub-Token", apiKey).build()
        return chain.proceed(request)
    }
}