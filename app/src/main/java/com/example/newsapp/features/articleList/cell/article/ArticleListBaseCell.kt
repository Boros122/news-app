package com.example.newsapp.features.articleList.cell.article

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.newsapp.R

interface ArticleListBaseCell {

    val root: ConstraintLayout
    val timeTextView: TextView
    val titleTextView: TextView
    val articleImageView: ImageView

    fun bind(context: Context, cellModel: ArticleListCellModel) {
        with(cellModel) {
            timeTextView.text = time
            titleTextView.text = title

            Glide.with(context)
                .load(imageUrl)
                .placeholder(AppCompatResources.getDrawable(context, R.drawable.img_defaul))
                .into(articleImageView)

            root.setOnClickListener {
                controller.onItemSelected(title)
            }
        }
    }

}