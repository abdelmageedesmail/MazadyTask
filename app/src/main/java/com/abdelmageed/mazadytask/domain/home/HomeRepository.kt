package com.abdelmageed.mazadytask.domain.home

import androidx.paging.PagingData
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.ErrorStatus
import com.abdelmageed.mazadytask.data.remote.response.GenresResponse
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getMovieList(): Flow<PagingData<ResultsItem>>
    suspend fun getMoviesGenres(): Flow<BaseResult<GenresResponse, ErrorStatus>>
    fun getFavorites(): Flow<List<FavoritesMovieEntity>>
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun addToFavorites(movie: FavoritesMovieEntity)
    suspend fun removeFromFavorites(movie: FavoritesMovieEntity)
}