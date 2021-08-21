package com.example.stocktest.data.network.base

import java.io.IOException

sealed class ApiError(override val message: String): IOException(message) {
    class RateLimitExceeded(message: String?) : ApiError(message ?: "Request limit exceeded")
    class UnknownError(message: String?) : ApiError(message ?: "Unknown error")
}
