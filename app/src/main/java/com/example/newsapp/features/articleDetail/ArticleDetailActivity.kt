package com.example.newsapp.features.articleDetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityArticleDetailBinding
import com.example.newsapp.shared.extensions.ActivityAnimationType
import com.example.newsapp.shared.extensions.applyAnimations
import com.example.newsapp.shared.extensions.reObserve
import com.example.newsapp.shared.ui.BaseActivity
import javax.inject.Inject


class ArticleDetailActivity : BaseActivity() {

    // region Properties

    private lateinit var binding: ActivityArticleDetailBinding

    // endregion

    // region Dependencies

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ArticleDetailViewModel> { viewModelFactory }

    // endregion

    // region Observers

    private val errorObserver = Observer<Boolean> {
        if (it) {
            binding.emptyScreenTextView.isVisible = true
        }
    }

    private val viewDataObserver = Observer<ArticleViewData> {
        processViewData(it)
    }

    // endregion

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        applyAnimations(ActivityAnimationType.PUSH_CLOSE)
    }

    // endregion

    // region Private methods

    private fun init() {
        initActivityData()
        initObservers()
        viewModel.fetchData()
    }

    private fun initActivityData() {
        intent.extras?.getParcelable<ArticleDetailActivityData>(ACTIVITY_DATA_KEY)?.let {
            viewModel.activityData = it
        }
    }

    private fun initObservers() {
        viewModel.errorLiveData.reObserve(this, errorObserver)
        viewModel.articleViewDataLiveData.reObserve(this, viewDataObserver)
    }

    private fun processViewData(viewData: ArticleViewData) {
        binding.apply {
            Glide.with(this@ArticleDetailActivity)
                .load(viewData.imageUrl)
                .placeholder(
                    AppCompatResources.getDrawable(
                        this@ArticleDetailActivity,
                        R.drawable.img_defaul
                    )
                )
                .into(articleImageView)

            titleTextView.text = viewData.title
            contentTextView.text = viewData.content
            backButton.setOnClickListener {
                onBackPressed()
            }
        }
    }

    // endregion

}