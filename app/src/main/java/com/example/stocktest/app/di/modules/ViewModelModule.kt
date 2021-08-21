package com.example.stocktest.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stocktest.viewmodels.MainViewModel
import com.example.stocktest.app.base.ViewModelFactory
import com.example.stocktest.app.base.ViewModelKey
import com.example.stocktest.viewmodels.LoginViewModel
import com.example.stocktest.viewmodels.StockInformationViewModel
import com.example.stocktest.viewmodels.StockListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StockListViewModel::class)
    abstract fun stockListViewModel(stockListViewModel: StockListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StockInformationViewModel::class)
    abstract fun stockInformationViewModel
                (stockInformationViewModel: StockInformationViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

