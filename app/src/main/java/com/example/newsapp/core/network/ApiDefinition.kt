package com.example.newsapp.core.network

import com.example.newsapp.core.model.ArticleList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiDefinition {

    @GET("everything")
    suspend fun getArticles(@QueryMap queryParameters: HashMap<String, String>): Response<ArticleList>

}