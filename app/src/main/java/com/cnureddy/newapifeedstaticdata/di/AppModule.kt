package com.cnureddy.newapifeedstaticdata.di

import com.cnureddy.newapifeedstaticdata.common.Constants
import com.cnureddy.newapifeedstaticdata.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NEWS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}