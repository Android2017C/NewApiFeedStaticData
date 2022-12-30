package com.cnureddy.newapifeedstaticdata.network

import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import com.cnureddy.newapifeedstaticdata.model.MovieItem
import com.cnureddy.newapifeedstaticdata.model.NewsApiResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovies(): List<MovieItem> = apiService.getAllMovies()
    suspend fun getNews(): NewsApiResponse = apiService.getNewsApi()
}