package com.abdelmageed.mazadytask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoritesMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster: String,
    val year: Int,
    val rating: Double,
    val genre: String,
    val actress: String?,
    val overview: String?,
    val runtime: Int?,
    val production: String?,
    val awards: String?

)