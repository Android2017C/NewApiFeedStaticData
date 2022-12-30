package com.cnureddy.newapifeedstaticdata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cnureddy.newapifeedstaticdata.common.Resource
import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import com.cnureddy.newapifeedstaticdata.model.MovieItem
import com.cnureddy.newapifeedstaticdata.model.NewsApiResponse
import com.cnureddy.newapifeedstaticdata.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val newsStateFlow: MutableStateFlow<Resource<NewsApiResponse>> =
        MutableStateFlow(Resource.Empty())
    val observeNewsStateFlow: StateFlow<Resource<NewsApiResponse>> = newsStateFlow

    fun getNewList() = viewModelScope.launch {
        newsStateFlow.value = Resource.Loading(null)
        newsRepository.getNewsData().catch { e ->
            newsStateFlow.value = Resource.Error(e.message.toString())
        }
            .collect { data ->
                newsStateFlow.value = Resource.Success(data)
            }
    }
}