package com.example.newsapp.core.network.utils

enum class Query(val value: String) {

    SEARCH_KEY("q"),
    SORT_KEY("sortBy"),
    SORT_VALUE("publishedAt"),
    PAGE_SIZE("pageSize"),
    PAGE("page")

}