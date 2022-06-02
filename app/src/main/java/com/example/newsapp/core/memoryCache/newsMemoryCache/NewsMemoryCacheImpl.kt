package com.example.newsapp.core.memoryCache.newsMemoryCache

import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsMemoryCacheImpl @Inject constructor(): NewsMemoryCache {

    private val pageSize = 20

    private val articlesMap = HashMap<String, ArticleList>()
    private val articleSet = HashSet<Article>()

    override fun isArticlesInMemory(keyword: String): Boolean {
        return articlesMap[keyword] != null
    }

    override fun getArticlesFromMemory(keyword: String): ArticleList? {
        return articlesMap[keyword]
    }

    override fun getArticleByTitleFromMemory(title: String): Article? {
        return articleSet.find { it.title == title }
    }

    override fun saveArticlesToMemory(keyword: String, articleList: ArticleList) {
        articlesMap[keyword] = articleList
        articleSet.addAll(articleList.articles)
    }

    override fun updateArticlesToMemory(keyword: String, articleList: ArticleList) {
        articlesMap[keyword]?.articles?.addAll(articleList.articles)
        articleSet.addAll(articleList.articles)
    }

    override fun getArticlesCurrentPage(keyword: String): Int {
        return (articlesMap[keyword]?.articles?.size ?: 0) / pageSize
    }

    override fun getArticlesPageSize(keyword: String): Int {
        return pageSize
    }
}