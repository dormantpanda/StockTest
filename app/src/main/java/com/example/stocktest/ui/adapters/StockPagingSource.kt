package com.example.stocktest.ui.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.stocktest.data.models.Stock
import com.example.stocktest.data.repositories.stock.StockListRepository
import java.lang.Exception

class StockPagingSource(private val stockRepo: StockListRepository) : PagingSource<Int, Stock>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Stock> {
        return try {
            val currentPage = params.key ?: 0
            val response = stockRepo.getStockList(
                EXCHANGE_CODE, PAGE_SIZE, currentPage * PAGE_SIZE
            )

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Stock>): Int? {
        return state.anchorPosition
        /*.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }*/
    }

    companion object {
        const val EXCHANGE_CODE = "ME"
        const val PAGE_SIZE = 10
    }
}
