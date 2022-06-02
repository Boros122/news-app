package com.example.newsapp.shared.navigation

import android.content.Context
import android.content.Intent
import com.example.newsapp.features.articleDetail.ArticleDetailActivity
import com.example.newsapp.features.articleDetail.ArticleDetailActivityData
import com.example.newsapp.shared.extensions.ActivityAnimationType
import com.example.newsapp.shared.extensions.applyAnimations
import com.example.newsapp.shared.ui.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityNavigationManagerImpl @Inject constructor() : ActivityNavigationManager {

    override fun navigateToArticleDetailActivity(context: Context?, articleId: String) {
        context ?: return
        Intent(context, ArticleDetailActivity::class.java).also { intent ->
            intent.putExtra(BaseActivity.ACTIVITY_DATA_KEY, ArticleDetailActivityData(articleId))
            context.startActivity(intent)
            (context as? BaseActivity)?.applyAnimations(ActivityAnimationType.PUSH_OPEN)
        }
    }

}