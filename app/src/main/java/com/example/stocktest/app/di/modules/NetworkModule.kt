package com.example.stocktest.app.di.modules

import com.example.stocktest.BuildConfig
import com.example.stocktest.data.network.ApiStock
import com.example.stocktest.data.network.interceptors.ErrorInterceptor
import com.example.stocktest.data.network.interceptors.TokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideTokenInterceptor() = TokenInterceptor()

    @Provides
    @Singleton
    fun provideErrorInterceptor() = ErrorInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideStockApi(retrofit: Retrofit): ApiStock =
        retrofit.create(ApiStock::class.java)
}