package com.example.stocktest.data.network.base

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val error : String) : State()
}