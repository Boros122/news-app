package com.example.newsapp.utils

import com.example.newsapp.core.model.Article
import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.model.ErrorResponse
import com.example.newsapp.core.model.Source
import java.util.*

object MockHelper {

    const val searchKeyword = "tesla"

    val article = Article(
        Source(null, "name"),
        author = "author",
        title = "title",
        description = "description",
        url = "url",
        urlToImage = "urlToImage",
        publishedAt = Date(),
        content = "content"
    )

    val articleList = ArticleList(
        status = "200",
        totalResults = 1,
        articles = arrayListOf(article)
    )

    val errorResponse = ErrorResponse("Error")

}