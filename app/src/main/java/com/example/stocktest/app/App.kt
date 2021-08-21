package com.example.stocktest.app

import com.example.stocktest.app.di.AppComponent
import com.example.stocktest.app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.factory().create(this)
        return appComponent
    }
}