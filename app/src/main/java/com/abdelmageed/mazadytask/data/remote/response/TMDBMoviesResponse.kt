package com.abdelmageed.mazadytask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class TMDBMoviesResponse(
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<ResultsItem> = emptyList(),
    val totalResults: Int? = null
)

@Serializable
data class ResultsItem(
    val overview: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    val genre_ids: List<Int?>? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val release_date: String? = null,
    val popularity: Double? = null,
    val vote_average: Double? = null,
    val id: Int? = null,
    val adult: Boolean? = null,
    val vote_count: Int? = null,
    var isFavoriteItem: Boolean = false,
    var genres: List<String> = emptyList()
)

