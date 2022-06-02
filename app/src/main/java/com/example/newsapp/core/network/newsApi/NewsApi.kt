package com.example.newsapp.core.network.newsApi

import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.repository.Result

interface NewsApi {

    suspend fun getArticles(
        keyword: String,
        pageSize: Int,
        page: Int
    ): Result<ArticleList>

}