package com.example.newsapp.features.articleList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.model.ArticleList
import com.example.newsapp.core.model.ErrorResponse
import com.example.newsapp.core.repository.Result
import com.example.newsapp.core.repository.newsRepository.NewsRepository
import com.example.newsapp.features.articleList.cell.article.ArticleListCellController
import com.example.newsapp.features.articleList.cell.article.ArticleListCellModel
import com.example.newsapp.features.articleList.cell.loading.LoadingCellController
import com.example.newsapp.features.articleList.cell.loading.LoadingCellModel
import com.example.newsapp.shared.adapter.BaseCellModel
import com.example.newsapp.shared.extensions.difference
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), ArticleListCellController, LoadingCellController {

    // region Properties

    private val initialSearchKeyword = "tesla"
    private var actualSearchKeyword: String? = null
    private var isNextPageLoadingInProgress = false

    // endregion

    // region Observables

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?>
        get() = _errorLiveData

    private val _cellModelsLiveData = MutableLiveData<ArrayList<BaseCellModel>>()
    val cellModelsLiveData: LiveData<ArrayList<BaseCellModel>>
        get() = _cellModelsLiveData

    private val _navigateToDetailLiveData = MutableLiveData<String?>()
    val navigateToDetailLiveData: LiveData<String?>
        get() = _navigateToDetailLiveData

    // endregion

    // region Public Methods

    fun fetchData() {
        if (newsRepository.isArticlesInMemory(getSearchKeyword())) {
            clearCellModels()
            processArticles(newsRepository.getArticlesFromMemory(getSearchKeyword()))
        } else {
            getFirstPage()
        }
    }

    fun search(searchKeyword: String) {
        actualSearchKeyword = searchKeyword
        fetchData()
    }

    fun getSearchKeyword() = (actualSearchKeyword ?: initialSearchKeyword).lowercase()

    fun clearError() {
        _errorLiveData.value = null
    }

    // endregion

    // region Private Methods

    private fun getFirstPage() {
        viewModelScope.launch {
            startLoading()
            newsRepository.getArticles(getSearchKeyword()).let { result ->
                endLoading()
                if (result is Result.Success) {
                    clearCellModels()
                    processArticles(result.data)
                } else if (result is Result.Error) {
                    showError(result.error)
                }
            }
        }
    }

    private fun getNextPage() {
        if (isNextPageLoadingInProgress)
            return
        viewModelScope.launch {
            isNextPageLoadingInProgress = true
            newsRepository.getArticlesNextPage(getSearchKeyword()).let { result ->
                isNextPageLoadingInProgress = false
                if (result is Result.Success) {
                    processArticles(result.data)
                } else if (result is Result.Error) {
                    showError(result.error)
                    removeLoadingCell()
                }
            }
        }
    }

    private fun processArticles(articleList: ArticleList?) {
        articleList ?: return
        updateCellModels(articleList)
    }

    private fun updateCellModels(articleList: ArticleList) {
        val cellModels: ArrayList<BaseCellModel> =
            ArrayList(
                _cellModelsLiveData.value?.filterIsInstance<ArticleListCellModel>() ?: ArrayList()
            )
        cellModels.addAll(createCellModels(articleList))
        if (articleList.hasNextPage()) {
            cellModels.add(createLoadingCellModel())
        }
        _cellModelsLiveData.value = cellModels
    }

    private fun removeLoadingCell() {
        _cellModelsLiveData.value = ArrayList(
            _cellModelsLiveData.value?.filterIsInstance<ArticleListCellModel>() ?: ArrayList()
        )
    }

    private fun createCellModels(articleList: ArticleList): ArrayList<BaseCellModel> {
        val cellModels = ArrayList<BaseCellModel>()
        articleList.articles.forEach {
            cellModels.add(
                ArticleListCellModel(
                    cellId = it.title,
                    title = it.title,
                    time = it.publishedAt.difference(),
                    imageUrl = it.urlToImage ?: "",
                    controller = this
                )
            )
        }
        return cellModels
    }

    private fun createLoadingCellModel(): LoadingCellModel {
        return LoadingCellModel(
            cellId = Long.MAX_VALUE.toString(),
            controller = this
        )
    }

    private fun clearCellModels() {
        _cellModelsLiveData.value?.clear()
    }

    private fun showError(errorResponse: ErrorResponse) {
        _errorLiveData.value = errorResponse.message
    }

    private fun startLoading() {
        _loadingLiveData.value = true
    }

    private fun endLoading() {
        _loadingLiveData.value = false
    }

    // endregion

    // region ArticleListCellController

    override fun onItemSelected(id: String) {
        _navigateToDetailLiveData.value = id
        _navigateToDetailLiveData.value = null
    }

    // endregion

    // region LoadingCellController

    override fun getNextPage(context: Context) {
        getNextPage()
    }

    // endregion

}