package com.example.newsapp.features.articleList.cell.loading

import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ViewHolderLoadingCellBinding

class LoadingCellViewHolder(binding: ViewHolderLoadingCellBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: LoadingCellModel) {
        model.controller.getNextPage(itemView.context)
    }
}