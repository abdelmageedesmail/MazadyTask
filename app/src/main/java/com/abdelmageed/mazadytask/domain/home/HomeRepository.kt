package com.abdelmageed.mazadytask.domain.home

import androidx.paging.PagingData
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getMovieList(): Flow<PagingData<MoviesResponseItem>>
    fun getFavorites(): Flow<List<FavoritesMovieEntity>>
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun addToFavorites(movie: FavoritesMovieEntity)
    suspend fun removeFromFavorites(movie: FavoritesMovieEntity)
}