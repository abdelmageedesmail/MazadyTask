package com.abdelmageed.mazadytask.data.remote.apiCall

import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.ErrorStatus
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import com.abdelmageed.mazadytask.data.remote.response.TMDBMoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesApiClient(
    private val httpClient: HttpClient
) {

    suspend fun getMovies(
        page: Int,
        limit: Int
    ): BaseResult<TMDBMoviesResponse/*List<ResultsItem>*/, ErrorStatus> {
        val response = httpClient.get("3/discover/movie"/*"api/v1/movies"*/) {
            parameter("api_key","ef23ebe3e8e1557eb8c3d947499fa0e2")
            parameter("page", "$page")
            parameter("limit", "$limit")
        }

        return when (response.status.value) {
            in 200..299 -> {
                BaseResult.Success(response.body())
            }

            in 400..499 -> {
                BaseResult.Error(ErrorStatus.REQUEST_ERROR)
            }

            in 500..599 -> {
                BaseResult.Error(ErrorStatus.SERVER_ERROR)
            }

            else -> {
                BaseResult.Error(ErrorStatus.UNKNOWN_ERROR)
            }
        }
    }
}