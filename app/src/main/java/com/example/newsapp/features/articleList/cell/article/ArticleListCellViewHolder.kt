package com.example.newsapp.features.articleList.cell.article

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ViewHolderArticleListCellBinding

class ArticleListCellViewHolder(
    binding: ViewHolderArticleListCellBinding,
) : RecyclerView.ViewHolder(binding.root), ArticleListBaseCell {

    override val root: ConstraintLayout = binding.root
    override val timeTextView: TextView = binding.timeTextView
    override val titleTextView: TextView = binding.titleTextView
    override val articleImageView: ImageView = binding.articleImageView

}