package com.example.newsapp.features.articleList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityArticleListBinding
import com.example.newsapp.shared.adapter.BaseCellModel
import com.example.newsapp.shared.extensions.reObserve
import com.example.newsapp.shared.navigation.ActivityNavigationManager
import com.example.newsapp.shared.ui.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class ArticleListActivity : BaseActivity() {

    // region Properties

    private lateinit var binding: ActivityArticleListBinding

    private lateinit var recyclerAdapter: ArticleListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // endregion

    // region Dependencies

    @Inject
    lateinit var activityNavigationManager: ActivityNavigationManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ArticleListViewModel> { viewModelFactory }

    // endregion

    // region Observers

    private val errorObserver = Observer<String?> { error ->
        error?.let {
            showError(it)
            viewModel.clearError()
        }
    }

    private val loadingObserver: Observer<Boolean> = Observer {
        if (it) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private val cellModelsObserver: Observer<ArrayList<BaseCellModel>> = Observer {
        if (it.isEmpty()) {
            showEmptyScreen()
        } else {
            showNotEmptyScreen()
            recyclerAdapter.autoNotify(it)
        }
    }

    private val navigateToDetailObserver = Observer<String?> { id ->
        id?.let {
            activityNavigationManager.navigateToArticleDetailActivity(this, id)
        }
    }

    // endregion

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        (menu?.findItem(R.id.searchItem)?.actionView as? SearchView)?.let { searchView ->
            searchView.queryHint = getString(R.string.search)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        clearAdapter()
                        viewModel.search(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    // endregion

    // region Private methods

    private fun init() {
        initRecyclerView()
        initObservers()
        viewModel.fetchData()
    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            adapter = ArticleListAdapter().also { recyclerAdapter = it }
            layoutManager = LinearLayoutManager(context).also { linearLayoutManager = it }
        }
    }

    private fun initObservers() {
        viewModel.errorLiveData.reObserve(this, errorObserver)
        viewModel.loadingLiveData.reObserve(this, loadingObserver)
        viewModel.cellModelsLiveData.reObserve(this, cellModelsObserver)
        viewModel.navigateToDetailLiveData.reObserve(this, navigateToDetailObserver)
    }

    private fun showEmptyScreen() {
        binding.emptyScreenTextView.isVisible = true
        binding.articlesRecyclerView.isVisible = false
    }

    private fun showNotEmptyScreen() {
        binding.emptyScreenTextView.isVisible = false
        binding.articlesRecyclerView.isVisible = true
    }

    private fun showLoading() {
        binding.loadingProgressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.loadingProgressBar.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearAdapter() {
        recyclerAdapter.clear()
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun showError(error: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.error))
            .setMessage(error)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // endregion

}