package com.example.stocktest.data.network

import com.example.stocktest.data.models.Symbol
import com.example.stocktest.data.models.responses.CandlesResponse
import com.example.stocktest.data.models.responses.ProfileResponse
import com.example.stocktest.data.models.responses.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStock {
    @GET("stock/symbol")
    suspend fun getSymbols(@Query("exchange") exchange: String): List<Symbol>

    @GET("stock/profile2")
    suspend fun getProfile(@Query("symbol") symbol: String): ProfileResponse

    @GET("quote")
    suspend fun getQuote(@Query("symbol") symbol: String): QuoteResponse

    @GET("stock/candle")
    suspend fun getCandle(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): CandlesResponse
}