package com.example.newsapp.core.network.newsApi

import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.network.ApiDefinition
import com.example.newsapp.core.network.utils.ApiErrorHandler
import com.example.newsapp.core.network.utils.Query
import com.example.newsapp.core.repository.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsApiImpl @Inject constructor(
    private val apiDefinition: ApiDefinition,
    private val apiErrorHandler: ApiErrorHandler
) : NewsApi {

    override suspend fun getArticles(keyword: String, pageSize: Int, page: Int): Result<ArticleList> {
        return try {
            val response = apiDefinition.getArticles(createQueryMap(keyword, pageSize, page))
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                val error = apiErrorHandler.createErrorResponse(response.errorBody())
                Result.Error(error)
            }
        } catch (e: Exception) {
            Result.Error(apiErrorHandler.createGeneralErrorResponse())
        }
    }

    private fun createQueryMap(keyword: String, pageSize: Int, page: Int): HashMap<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap[Query.SEARCH_KEY.value] = keyword
        queryMap[Query.SORT_KEY.value] = Query.SORT_VALUE.value
        queryMap[Query.PAGE_SIZE.value] = pageSize.toString()
        queryMap[Query.PAGE.value] = page.toString()
        return queryMap
    }

}