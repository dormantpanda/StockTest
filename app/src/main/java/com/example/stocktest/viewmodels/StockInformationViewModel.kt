package com.example.stocktest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.stocktest.app.base.BaseViewModel
import com.example.stocktest.data.models.Stock
import com.example.stocktest.data.repositories.stock.StockListRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class StockInformationViewModel @Inject constructor(
    private val stockListRepository: StockListRepository
) : BaseViewModel() {

    var candlesResponse = MutableLiveData<List<Float>>()

    fun getStockCandles(symbol: String) {
        viewModelScope.launch {
            val response =
                stockListRepository.getCandle(symbol, RESOLUTION, getPrevTime(), getCurrentTime())
            response.closePrices?.let {
                candlesResponse.postValue(it)
            }
        }
    }

    private fun getCurrentTime(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis / 1000
    }

    private fun getPrevTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return calendar.timeInMillis / 1000
    }

    companion object {
        const val RESOLUTION = "D" // available 15, 30, 60, D, M
    }
}