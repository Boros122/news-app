package com.example.newsapp.core.model

import java.util.*

data class Article(
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: Date,
    val content: String? = null,
)