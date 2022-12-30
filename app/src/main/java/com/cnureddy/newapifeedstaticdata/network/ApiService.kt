package com.cnureddy.newapifeedstaticdata.network

import com.cnureddy.newapifeedstaticdata.model.ArticlesItem
import com.cnureddy.newapifeedstaticdata.model.MovieItem
import com.cnureddy.newapifeedstaticdata.model.NewsApiResponse
import retrofit2.http.GET

interface ApiService {
    /*  @GET("/Android/news-api-feed/staticResponse.json")
      suspend fun getNewsApi() : List<NewsBaseResponse>*/
    @GET("movielist.json")
    suspend fun getAllMovies(): List<MovieItem>

    @GET("Android/news-api-feed/staticResponse.json")
    suspend fun getNewsApi(): NewsApiResponse
}