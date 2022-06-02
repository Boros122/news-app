package com.example.newsapp.core.repository.newsRepository

import com.example.newsapp.core.memoryCache.newsMemoryCache.NewsMemoryCache
import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.network.newsApi.NewsApi
import com.example.newsapp.core.repository.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsMemoryCache: NewsMemoryCache,
    private val newsApi: NewsApi
): NewsRepository {

    override fun isArticlesInMemory(keyword: String): Boolean {
        return newsMemoryCache.isArticlesInMemory(keyword)
    }

    override fun getArticlesFromMemory(keyword: String): ArticleList? {
        return newsMemoryCache.getArticlesFromMemory(keyword)
    }

    override fun getArticleByTitleFromMemory(title: String): Article? {
        return newsMemoryCache.getArticleByTitleFromMemory(title)
    }

    override suspend fun getArticles(keyword: String): Result<ArticleList> {
        val pageSize = newsMemoryCache.getArticlesPageSize(keyword)
        val nextPage = newsMemoryCache.getArticlesCurrentPage(keyword) + 1
        val result = newsApi.getArticles(keyword, pageSize, nextPage)
        if (result is Result.Success) {
            newsMemoryCache.saveArticlesToMemory(keyword, result.data)
        }
        return result
    }

    override suspend fun getArticlesNextPage(keyword: String): Result<ArticleList> {
        val pageSize = newsMemoryCache.getArticlesPageSize(keyword)
        val nextPage = newsMemoryCache.getArticlesCurrentPage(keyword) + 1
        val result = newsApi.getArticles(keyword, pageSize, nextPage)
        if (result is Result.Success) {
            newsMemoryCache.updateArticlesToMemory(keyword, result.data)
        }
        return result
    }

}