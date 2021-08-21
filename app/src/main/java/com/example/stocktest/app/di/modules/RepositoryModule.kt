package com.example.stocktest.app.di.modules

import com.example.stocktest.data.network.ApiStock
import com.example.stocktest.data.repositories.stock.IStockListRepository
import com.example.stocktest.data.repositories.stock.StockListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideStockListRepository(stockApi: ApiStock): IStockListRepository =
        StockListRepository(stockApi)
}