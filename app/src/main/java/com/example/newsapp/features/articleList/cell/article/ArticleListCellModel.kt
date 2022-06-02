package com.example.newsapp.features.articleList.cell.article

import com.example.newsapp.shared.adapter.BaseCellModel

data class ArticleListCellModel(
    override val cellId: String,
    val time: String,
    val title: String,
    val imageUrl: String,
    val controller: ArticleListCellController
): BaseCellModel