package com.abdelmageed.mazadytask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val moviesResponse: List<MoviesResponseItem?>? = null
)


@Serializable
data class MoviesResponseItem(
    val country: String? = null,
    val website: String? = null,
    val year: Int? = null,
    val production: String? = null,
    val director: String? = null,
    val rating: Double? = null,
    val runtime: Int? = null,
    val language: String? = null,
    val title: String? = null,
    val trailer: String? = null,
    val actors: List<String?>? = null,
    val plot: String? = null,
    val awards: String? = null,
    val genre: List<String?>? = null,
    val id: Int? = null,
    val boxOffice: String? = null,
    val poster: String? = null,
    var isFavoriteItem: Boolean = false
)

