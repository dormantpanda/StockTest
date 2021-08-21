package com.example.stocktest.data.repositories.stock

import com.example.stocktest.data.models.AsyncStock
import com.example.stocktest.data.models.Stock
import com.example.stocktest.data.models.Symbol
import com.example.stocktest.data.models.responses.CandlesResponse
import com.example.stocktest.data.models.responses.ProfileResponse
import com.example.stocktest.data.models.responses.QuoteResponse
import com.example.stocktest.data.models.responses.SymbolsResponse
import com.example.stocktest.data.network.ApiStock
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.math.min

class StockListRepository @Inject constructor(
    private val stockApi: ApiStock
) : IStockListRepository {

    private var symbolsList: List<String> = listOf()

    override suspend fun getStockList(exchange: String, limit: Int, offset: Int): List<Stock> {
        return coroutineScope {
            if (symbolsList.isNullOrEmpty()) {
                symbolsList = stockApi.getSymbols(exchange)
                    .map { it.symbol.substringBefore('.').substringBefore('-') }
                    .sorted()
            }
            val asyncList = mutableListOf<AsyncStock>()
            for (i in offset until min((offset + limit), symbolsList.size)) {
                val currentSymbol = symbolsList[i]
                asyncList.add(
                    AsyncStock(
                        symbol = currentSymbol,
                        quote = async {
                            try {
                                return@async stockApi.getQuote(currentSymbol)
                            } catch (e: Exception) {
                                return@async null
                            }
                        },
                        profile = async { stockApi.getProfile(currentSymbol) }
                    )
                )
            }
            return@coroutineScope asyncList.map {
                Stock(
                    symbol = it.symbol,
                    price = it.quote.await()?.current,
                    change = it.quote.await()?.change,
                    logo = it.profile.await()?.logo
                )
            }
        }
    }

    override suspend fun getSymbols(exchange: String): List<Symbol> = stockApi.getSymbols(exchange)

    override suspend fun getProfile(symbol: String): ProfileResponse = stockApi.getProfile(symbol)

    override suspend fun getQuote(symbol: String): QuoteResponse = stockApi.getQuote(symbol)

    override suspend fun getCandle(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CandlesResponse = stockApi.getCandle(symbol, resolution, from, to)
}