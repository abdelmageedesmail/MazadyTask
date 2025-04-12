package com.abdelmageed.mazadytask.extension

import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.model.MovieDto
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem

fun ResultsItem.toMovieDto() = MovieDto(
    id = this.id ?: 0,
    title = this.title ?: "",
    poster = this.poster_path ?: "",
    year = this.release_date ?: "",
    genres = this.genres.joinToString(", "),
    overview = this.overview,
    rating = this.vote_average
)

fun ResultsItem.toFavoriteEntity(): FavoritesMovieEntity {
    return FavoritesMovieEntity(
        id = this.id ?: 0,
        title = this.title ?: "",
        poster = this.poster_path ?: "",
        year = this.release_date ?: "",
        rating = this.vote_average ?: 0.0,
        genre = this.genres.joinToString(", "),
        overview = this.overview ?: ""
    )
}


fun FavoritesMovieEntity.toMovieDto() = MovieDto(
    id = this.id,
    title = this.title,
    poster = this.poster,
    year = this.year,
    genres = this.genre,
    rating = this.rating,
    overview = this.overview
)

fun MovieDto.toFavoriteEntity() = FavoritesMovieEntity(
    id = this.id ?: 0,
    title = this.title ?: "",
    poster = this.poster ?: "",
    year = "",
    rating = this.rating ?: 0.0,
    genre = this.genres ?: "",
    overview = overview,
)
