package com.example.newsapp.core.memoryCache.newsMemoryCache

import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList

interface NewsMemoryCache {

    fun isArticlesInMemory(keyword: String): Boolean

    fun getArticlesFromMemory(keyword: String): ArticleList?

    fun getArticleByTitleFromMemory(title: String): Article?

    fun saveArticlesToMemory(keyword: String, articleList: ArticleList)

    fun updateArticlesToMemory(keyword: String, articleList: ArticleList)

    fun getArticlesCurrentPage(keyword: String): Int

    fun getArticlesPageSize(keyword: String): Int

}