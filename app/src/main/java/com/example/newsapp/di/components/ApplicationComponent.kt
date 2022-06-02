package com.example.newsapp.di.components

import android.content.Context
import com.example.newsapp.NewsApplication
import com.example.newsapp.di.ViewModelBuilder
import com.example.newsapp.di.modules.ApiModule
import com.example.newsapp.di.modules.MainModule
import com.example.newsapp.di.modules.ManagerModule
import com.example.newsapp.di.modules.MemoryCacheModule
import com.example.newsapp.di.modules.NetworkModule
import com.example.newsapp.di.modules.RepositoryModule
import com.example.newsapp.features.articleDetail.ArticleDetailActivity
import com.example.newsapp.features.articleList.ArticleListActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        MainModule::class,
        ManagerModule::class,
        MemoryCacheModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelBuilder::class,
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(app: NewsApplication)

    fun inject(activity: ArticleListActivity)
    fun inject(activity: ArticleDetailActivity)

}