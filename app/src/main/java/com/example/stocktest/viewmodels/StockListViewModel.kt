package com.example.stocktest.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.stocktest.app.base.BaseViewModel
import com.example.stocktest.data.models.Stock
import com.example.stocktest.data.repositories.stock.StockListRepository
import com.example.stocktest.ui.adapters.StockPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockListViewModel @Inject constructor(
    private val stockListRepository: StockListRepository
) : BaseViewModel() {

    val stocks: Flow<PagingData<Stock>> = Pager(PagingConfig(pageSize = StockPagingSource.PAGE_SIZE)) {
        StockPagingSource(stockListRepository)
    }.flow.cachedIn(viewModelScope)
}