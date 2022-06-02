package com.example.newsapp.features.articleDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleDetailActivityData(
    val articleId: String
): Parcelable