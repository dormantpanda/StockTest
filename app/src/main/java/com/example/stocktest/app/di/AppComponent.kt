package com.example.stocktest.app.di

import com.example.stocktest.app.App
import com.example.stocktest.app.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App>{
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: App): AppComponent
    }
}