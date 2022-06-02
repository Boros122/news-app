package com.example.newsapp.core.repository.newsRepository

import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.repository.Result

interface NewsRepository {

    fun isArticlesInMemory(keyword: String): Boolean

    fun getArticlesFromMemory(keyword: String): ArticleList?

    fun getArticleByTitleFromMemory(title: String): Article?

    suspend fun getArticles(
        keyword: String
    ): Result<ArticleList>

    suspend fun getArticlesNextPage(
        keyword: String
    ): Result<ArticleList>

}