package com.example.newsapp.di.modules

import com.example.newsapp.core.memoryCache.newsMemoryCache.NewsMemoryCache
import com.example.newsapp.core.memoryCache.newsMemoryCache.NewsMemoryCacheImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MemoryCacheModule {

    @Binds
    abstract fun provideNewsMemoryCache(newsMemoryCache: NewsMemoryCacheImpl): NewsMemoryCache

}
