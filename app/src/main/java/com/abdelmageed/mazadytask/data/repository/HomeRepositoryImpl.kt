package com.abdelmageed.mazadytask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abdelmageed.mazadytask.data.local.FavoriteMovieDao
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.apiCall.MoviesApiClient
import com.abdelmageed.mazadytask.data.remote.apiCall.MovingPagingResource
import com.abdelmageed.mazadytask.data.remote.response.ErrorStatus
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponse
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.domain.home.HomeRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val httpClient: MoviesApiClient,
    private val dao: FavoriteMovieDao
) :
    HomeRepository {
    override suspend fun getMovieList(): Flow<PagingData<MoviesResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, prefetchDistance = 5, enablePlaceholders = false
            ),
            pagingSourceFactory = { MovingPagingResource(httpClient) }
        ).flow
    }

    override fun getFavorites(): Flow<List<FavoritesMovieEntity>> = dao.getAllFavorites()

    override fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)

    override suspend fun addToFavorites(movie: FavoritesMovieEntity) = dao.insert(movie)

    override suspend fun removeFromFavorites(movie: FavoritesMovieEntity) = dao.delete(movie)
}