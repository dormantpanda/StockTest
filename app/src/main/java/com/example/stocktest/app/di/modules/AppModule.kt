package com.example.stocktest.app.di.modules

import android.content.Context
import com.example.stocktest.app.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplicationContext(application: App): Context = application.applicationContext

    @Provides
    fun provideApplication(app: App): App = app
}