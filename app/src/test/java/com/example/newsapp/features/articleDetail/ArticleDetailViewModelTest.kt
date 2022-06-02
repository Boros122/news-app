package com.example.newsapp.features.articleDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.core.repository.newsRepository.NewsRepository
import com.example.newsapp.core.repository.newsRepository.NewsRepositoryCoach
import com.example.newsapp.utils.MainCoroutineRule
import com.example.newsapp.utils.MockHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
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
class ArticleDetailViewModelTest {

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
    private lateinit var viewModel: ArticleDetailViewModel

    private lateinit var newsRepositoryCoach: NewsRepositoryCoach

    @Before
    fun setUp() {
        newsRepositoryCoach = NewsRepositoryCoach(newsRepository)
        viewModel.activityData = ArticleDetailActivityData(MockHelper.searchKeyword)
    }

    @Test
    fun `If fetch data is successful, then set view data`() = runBlockingTest {
        // Arrange
        val keyword = MockHelper.searchKeyword
        val article = MockHelper.article
        newsRepositoryCoach.trainGetArticleFromMemory(keyword, article)

        // Act
        viewModel.fetchData()

        // Assert
        val liveDataValue = viewModel.articleViewDataLiveData.value
        assertNotNull(liveDataValue)
        assertEquals(liveDataValue?.imageUrl, article.urlToImage)
        assertEquals(liveDataValue?.content, article.content)
        assertEquals(liveDataValue?.title, article.title)
    }

    @Test
    fun `If fetch data is not successful, then set error`() = runBlockingTest {
        // Arrange
        val keyword = MockHelper.searchKeyword
        newsRepositoryCoach.trainGetArticleFromMemory(keyword, null)

        // Act
        viewModel.fetchData()

        // Assert
        assertNull(viewModel.articleViewDataLiveData.value)
        assertTrue(viewModel.errorLiveData.value ?: false)
    }

}