package com.cnureddy.newapifeedstaticdata.repository

import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import com.cnureddy.newapifeedstaticdata.model.MovieItem
import com.cnureddy.newapifeedstaticdata.model.NewsApiResponse
import com.cnureddy.newapifeedstaticdata.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    fun getMoviesData(): Flow<List<MovieItem>> = flow {
        emit(apiServiceImpl.getMovies())
    }.flowOn(Dispatchers.IO)

    fun getNewsData(): Flow<NewsApiResponse> = flow {
        emit(apiServiceImpl.getNews())
    }.flowOn(Dispatchers.IO)
}