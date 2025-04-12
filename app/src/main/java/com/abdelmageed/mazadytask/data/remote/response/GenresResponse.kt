package com.abdelmageed.mazadytask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenresItem?>? = null
)

@Serializable
data class GenresItem(
    val name: String? = null,
    val id: Int? = null
)

