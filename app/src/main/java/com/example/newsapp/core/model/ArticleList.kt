package com.example.newsapp.core.model

data class ArticleList(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Article>
) {

    fun hasNextPage(): Boolean {
        return articles.size < totalResults
    }

}