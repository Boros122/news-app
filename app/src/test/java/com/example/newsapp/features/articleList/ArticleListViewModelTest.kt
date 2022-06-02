package com.example.newsapp.features.articleList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.core.repository.Result
import com.example.newsapp.core.repository.newsRepository.NewsRepository
import com.example.newsapp.core.repository.newsRepository.NewsRepositoryCoach
import com.example.newsapp.utils.MainCoroutineRule
import com.example.newsapp.utils.MockHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class ArticleListViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var newsRepository: NewsRepository

    // Subject under test
    @InjectMocks
    private lateinit var viewModel: ArticleListViewModel

    private lateinit var newsRepositoryCoach: NewsRepositoryCoach

    @Before
    fun setUp() {
        newsRepositoryCoach = NewsRepositoryCoach(newsRepository)
    }

    @Test
    fun `If fetch data from network is successful, then set view data`() = runBlockingTest {
        // Arrange
        val keyword = MockHelper.searchKeyword
        val articleList = MockHelper.articleList
        newsRepositoryCoach.trainGetArticles(keyword, Result.Success(articleList))
        newsRepositoryCoach.trainIsArticlesInMemory(keyword, false)

        // Act
        viewModel.fetchData()

        // Assert
        assertNotNull(viewModel.cellModelsLiveData.value)
        assertFalse(viewModel.loadingLiveData.value ?: false)
    }

    @Test
    fun `If fetch data from network is not successful, then set error`() = runBlockingTest {
        // Arrange
        val keyword = MockHelper.searchKeyword
        val errorResponse = MockHelper.errorResponse
        newsRepositoryCoach.trainGetArticles(keyword, Result.Error(errorResponse))
        newsRepositoryCoach.trainIsArticlesInMemory(keyword, false)

        // Act
        viewModel.fetchData()

        // Assert
        assertNull(viewModel.cellModelsLiveData.value)
        assertEquals(viewModel.errorLiveData.value, errorResponse.message)
    }

    @Test
    fun `If fetch data from cache is successful, then set view data`() = runBlockingTest {
        // Arrange
        val keyword = MockHelper.searchKeyword
        val articleList = MockHelper.articleList
        newsRepositoryCoach.trainGetArticlesFromMemory(keyword, articleList)
        newsRepositoryCoach.trainIsArticlesInMemory(keyword, true)

        // Act
        viewModel.fetchData()

        // Assert
        assertNotNull(viewModel.cellModelsLiveData.value)
    }

    @Test
    fun `If search called, then update actual search keyword`() = runBlockingTest {
        // Arrange
        val searchKeyword = MockHelper.searchKeyword

        // Act
        viewModel.search(searchKeyword)

        // Assert
        assertEquals(searchKeyword, viewModel.getSearchKeyword())
    }

}