package com.example.stocktest.app.di.modules

import com.example.stocktest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}