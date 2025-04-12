package com.abdelmageed.mazadytask.data.remote.apiCall

import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.ErrorStatus
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
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
    ): BaseResult<List<MoviesResponseItem>, ErrorStatus> {
        val response = httpClient.get("api/v1/movies") {
            parameter("page", "$page")
            parameter("limit", "$limit")
        }

        return when (response.status.value) {
            in 200..299 -> {
                BaseResult.Success(response.body() as List<MoviesResponseItem>)
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