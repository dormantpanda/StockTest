package com.example.stocktest.app.di.modules

import com.example.stocktest.ui.LoginFragment
import com.example.stocktest.ui.StockInformationFragment
import com.example.stocktest.ui.StockListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun stockListFragment(): StockListFragment

    @ContributesAndroidInjector
    abstract fun stockInformationFragment(): StockInformationFragment
}