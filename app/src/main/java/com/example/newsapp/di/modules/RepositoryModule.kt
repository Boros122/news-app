package com.example.newsapp.di.modules

import com.example.newsapp.core.repository.newsRepository.NewsRepository
import com.example.newsapp.core.repository.newsRepository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository

}
