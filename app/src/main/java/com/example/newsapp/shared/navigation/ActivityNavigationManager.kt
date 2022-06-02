package com.example.newsapp.shared.navigation

import android.content.Context

interface ActivityNavigationManager {

    fun navigateToArticleDetailActivity(context: Context?, articleId: String)

}