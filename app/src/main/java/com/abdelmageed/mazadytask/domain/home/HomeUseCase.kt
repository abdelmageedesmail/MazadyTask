package com.abdelmageed.mazadytask.domain.home

import androidx.paging.PagingData
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.ErrorStatus
import com.abdelmageed.mazadytask.data.remote.response.GenresResponse
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val moviesRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<PagingData<ResultsItem>> =
        moviesRepository.getMovieList()

    suspend fun getMoviesGears(): Flow<BaseResult<GenresResponse, ErrorStatus>> =
        moviesRepository.getMoviesGenres()
}

class LocaleMoviesUseCase @Inject constructor(private val moviesRepository: HomeRepository) {

    fun getFavorites(): Flow<List<FavoritesMovieEntity>> = moviesRepository.getFavorites()

    fun isFavorite(id: Int): Flow<Boolean> = moviesRepository.isFavorite(id)

    suspend fun addToFavorites(movie: FavoritesMovieEntity) = moviesRepository.addToFavorites(movie)

    suspend fun removeFromFavorites(movie: FavoritesMovieEntity) =
        moviesRepository.removeFromFavorites(movie)
}