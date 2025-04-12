package com.abdelmageed.mazadytask.data.di

import com.abdelmageed.mazadytask.data.local.FavoriteMovieDao
import com.abdelmageed.mazadytask.data.remote.apiCall.MoviesApiClient
import com.abdelmageed.mazadytask.data.repository.HomeRepositoryImpl
import com.abdelmageed.mazadytask.domain.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieApiClient(httpClient: HttpClient): MoviesApiClient = MoviesApiClient(httpClient)

    @Provides
    @Singleton
    fun provideHomeRepository(
        apiClient: MoviesApiClient,
        httpClient: HttpClient,
        favoriteMovieDao: FavoriteMovieDao
    ): HomeRepository =
        HomeRepositoryImpl(apiClient, httpClient, favoriteMovieDao)

}