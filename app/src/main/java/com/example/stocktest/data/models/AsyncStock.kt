package com.example.stocktest.data.models

import com.example.stocktest.data.models.responses.ProfileResponse
import com.example.stocktest.data.models.responses.QuoteResponse
import kotlinx.coroutines.Deferred

data class AsyncStock(
    val symbol: String,
    val profile: Deferred<ProfileResponse?>,
    val quote: Deferred<QuoteResponse?>
)