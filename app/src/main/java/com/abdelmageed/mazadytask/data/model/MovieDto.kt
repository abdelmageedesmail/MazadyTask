package com.abdelmageed.mazadytask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
    val id: Int,
    val title: String?,
    val year: Int?,
    val poster: String?,
    val genres: String?,
    val actors: String?,
    val overview: String?,
    val runtime: Int?,
    val production: String?,
    val awards: String?,
    val isFavoriteItem: Boolean = false
) : Parcelable
