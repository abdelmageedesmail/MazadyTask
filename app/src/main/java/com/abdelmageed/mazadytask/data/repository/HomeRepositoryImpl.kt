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
import com.abdelmageed.mazadytask.data.remote.response.GenresResponse
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import com.abdelmageed.mazadytask.domain.home.HomeRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiClient: MoviesApiClient,
    private val httpClient: HttpClient,
    private val dao: FavoriteMovieDao
) :
    HomeRepository {
    override suspend fun getMovieList(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, prefetchDistance = 5, enablePlaceholders = false
            ),
            pagingSourceFactory = { MovingPagingResource(apiClient) }
        ).flow
    }

    override suspend fun getMoviesGenres(): Flow<BaseResult<GenresResponse, ErrorStatus>> = flow {
        try {
            val response = httpClient.get("3/genre/movie/list") {
                parameter("api_key", "ef23ebe3e8e1557eb8c3d947499fa0e2")
            }

            when (response.status.value) {
                in 200..299 -> {
                    emit(BaseResult.Success(response.body()))
                }

                in 400..499 -> {
                    emit(BaseResult.Error(ErrorStatus.REQUEST_ERROR))
                }

                in 500..599 -> {
                    emit(BaseResult.Error(ErrorStatus.SERVER_ERROR))
                }

                else -> {
                    emit(BaseResult.Error(ErrorStatus.UNKNOWN_ERROR))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
//            emit(BaseResult.Error(ErrorStatus.UNKNOWN_ERROR))
        }
    }

    override fun getFavorites(): Flow<List<FavoritesMovieEntity>> = dao.getAllFavorites()

    override fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)

    override suspend fun addToFavorites(movie: FavoritesMovieEntity) = dao.insert(movie)

    override suspend fun removeFromFavorites(movie: FavoritesMovieEntity) = dao.delete(movie)
}