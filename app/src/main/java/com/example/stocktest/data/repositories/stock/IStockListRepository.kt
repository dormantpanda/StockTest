package com.example.stocktest.data.repositories.stock

import com.example.stocktest.data.models.Stock
import com.example.stocktest.data.models.Symbol
import com.example.stocktest.data.models.responses.CandlesResponse
import com.example.stocktest.data.models.responses.ProfileResponse
import com.example.stocktest.data.models.responses.QuoteResponse
import com.example.stocktest.data.models.responses.SymbolsResponse

interface IStockListRepository {
    suspend fun getStockList(exchange: String, limit: Int, offset: Int): List<Stock>

    suspend fun getSymbols(exchange: String): List<Symbol>

    suspend fun getProfile(symbol: String): ProfileResponse

    suspend fun getQuote(symbol: String): QuoteResponse

    suspend fun getCandle(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CandlesResponse
}