package com.example.newsapp.features.articleDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.core.model.Article
import com.example.newsapp.core.repository.newsRepository.NewsRepository
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    // region Properties

    lateinit var activityData: ArticleDetailActivityData

    // endregion

    // region Observables

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private val _articleViewDataLiveData = MutableLiveData<ArticleViewData>()
    val articleViewDataLiveData: LiveData<ArticleViewData>
        get() = _articleViewDataLiveData

    // endregion

    // region Public Methods

    fun fetchData() {
        newsRepository.getArticleByTitleFromMemory(activityData.articleId).let {
            if (it != null) {
                processData(it)
            } else {
                showError()
            }
        }
    }

    // endregion

    // region Private Methods

    private fun processData(article: Article) {
        _articleViewDataLiveData.value = ArticleViewData(
            imageUrl = article.urlToImage ?: "",
            title = article.title,
            content = article.content ?: ""
        )
    }

    private fun showError() {
        _errorLiveData.value = true
    }

    // endregion

}