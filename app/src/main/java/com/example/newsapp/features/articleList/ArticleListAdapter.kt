package com.example.newsapp.features.articleList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ViewHolderArticleListCellBinding
import com.example.newsapp.databinding.ViewHolderArticleListHeaderCellBinding
import com.example.newsapp.databinding.ViewHolderLoadingCellBinding
import com.example.newsapp.features.articleList.cell.article.ArticleListCellModel
import com.example.newsapp.features.articleList.cell.article.ArticleListCellViewHolder
import com.example.newsapp.features.articleList.cell.article.ArticleListHeaderCellViewHolder
import com.example.newsapp.features.articleList.cell.loading.LoadingCellModel
import com.example.newsapp.features.articleList.cell.loading.LoadingCellViewHolder
import com.example.newsapp.shared.adapter.BaseCellModel
import com.example.newsapp.shared.adapter.RecyclerViewAdapterBase

class ArticleListAdapter :
    RecyclerViewAdapterBase<BaseCellModel, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_CELL -> {
                val binding = ViewHolderArticleListHeaderCellBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                ArticleListHeaderCellViewHolder(binding)
            }
            LOADING_CELL -> {
                val binding = ViewHolderLoadingCellBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingCellViewHolder(binding)
            }
            else -> {
                val binding = ViewHolderArticleListCellBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                ArticleListCellViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        when {
            itemType == HEADER_CELL && holder is ArticleListHeaderCellViewHolder -> {
                holder.bind(holder.itemView.context, items[position] as ArticleListCellModel)
            }
            itemType == NORMAL_CELL && holder is ArticleListCellViewHolder -> {
                holder.bind(holder.itemView.context, items[position] as ArticleListCellModel)
            }
            itemType == LOADING_CELL && holder is LoadingCellViewHolder -> {
                holder.bind(items[position] as LoadingCellModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> HEADER_CELL
            items[position] is LoadingCellModel -> LOADING_CELL
            else -> NORMAL_CELL
        }
    }

    companion object {
        private const val HEADER_CELL = 0
        private const val NORMAL_CELL = 1
        private const val LOADING_CELL = 2
    }

}