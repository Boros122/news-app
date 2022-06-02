package com.example.newsapp.di.modules

import com.example.newsapp.core.network.newsApi.NewsApi
import com.example.newsapp.core.network.newsApi.NewsApiImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ApiModule {

    @Binds
    abstract fun provideNewApi(newsApi: NewsApiImpl): NewsApi

}