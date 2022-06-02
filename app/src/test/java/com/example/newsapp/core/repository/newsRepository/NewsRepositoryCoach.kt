package com.example.newsapp.core.repository.newsRepository

import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.repository.Result
import com.nhaarman.mockitokotlin2.whenever

class NewsRepositoryCoach(private val newsRepository: NewsRepository) {

    suspend fun trainGetArticles(keyword: String, returnValue: Result<ArticleList>) {
        whenever(newsRepository.getArticles(keyword)).thenReturn(returnValue)
    }

    fun trainIsArticlesInMemory(keyword: String, returnValue: Boolean) {
        whenever(newsRepository.isArticlesInMemory(keyword)).thenReturn(returnValue)
    }

    fun trainGetArticlesFromMemory(keyword: String, returnValue: ArticleList?) {
        whenever(newsRepository.getArticlesFromMemory(keyword)).thenReturn(returnValue)
    }

    fun trainGetArticleFromMemory(title: String, returnValue: Article?) {
        whenever(newsRepository.getArticleByTitleFromMemory(title)).thenReturn(returnValue)
    }
}