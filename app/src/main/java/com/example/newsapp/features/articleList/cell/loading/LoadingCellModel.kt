package com.example.newsapp.features.articleList.cell.loading

import com.example.newsapp.shared.adapter.BaseCellModel

data class LoadingCellModel(
    override val cellId: String,
    val controller: LoadingCellController
) : BaseCellModel