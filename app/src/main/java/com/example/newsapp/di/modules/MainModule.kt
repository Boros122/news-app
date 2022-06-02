package com.example.newsapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.newsapp.di.ViewModelKey
import com.example.newsapp.features.articleDetail.ArticleDetailViewModel
import com.example.newsapp.features.articleList.ArticleListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    abstract fun bindArticleListViewModel(viewModel: ArticleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailViewModel::class)
    abstract fun bindArticleDetailViewModel(viewModel: ArticleDetailViewModel): ViewModel

}
