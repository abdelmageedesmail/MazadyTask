package com.abdelmageed.mazadytask.data.remote.apiCall

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem

class MovingPagingResource(private val apiClient: MoviesApiClient) :
    PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val limit = params.loadSize
//        val limit = 10

        return when (val result = apiClient.getMovies(page, limit)) {
            is BaseResult.Success -> {
                val data = result.data.results
                val nextKey = if (data.isEmpty()) null
                else {
                    page + 1
                }


                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            is BaseResult.Error -> {
                LoadResult.Error(Throwable("API Error: ${result.error}"))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}