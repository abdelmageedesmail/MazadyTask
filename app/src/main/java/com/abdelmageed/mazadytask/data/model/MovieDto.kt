package com.abdelmageed.mazadytask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto(
    val id: Int,
    val title: String?,
    val year: String?,
    val poster: String?,
    val genres: String?,
    val rating: Double?,
    val overview: String?,

    val isFavoriteItem: Boolean = false
) : Parcelable
