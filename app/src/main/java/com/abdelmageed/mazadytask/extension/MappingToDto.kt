package com.abdelmageed.mazadytask.extension

import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.model.MovieDto
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem

fun MoviesResponseItem.toFavoriteEntity(): FavoritesMovieEntity {
    return FavoritesMovieEntity(
        id = this.id ?: 0,
        title = this.title ?: "",
        poster = this.poster ?: "",
        year = this.year ?: 0,
        rating = this.rating ?: 0.0,
        genre = this.genre?.joinToString(", ") ?: "",
        actress = this.actors?.joinToString(",") ?: "",
        overview = this.plot ?: "",
        runtime = this.runtime ?: 0,
        production = this.production ?: "",
        awards = this.awards ?: ""
    )
}

fun MoviesResponseItem.toMovieDto() = MovieDto(
    id = this.id ?: 0,
    title = this.title ?: "",
    poster = this.poster ?: "",
    year = this.year ?: 0,
    genres = this.genre?.joinToString(", ") ?: "",
    actors = this.actors?.joinToString(", ") ?: "",
    overview = this.plot ?: "",
    runtime = this.runtime ?: 0,
    production = this.production ?: "",
    awards = this.awards ?: ""
)

fun FavoritesMovieEntity.toMovieDto() = MovieDto(
    id = this.id ?: 0,
    title = this.title ?: "",
    poster = this.poster ?: "",
    year = this.year ?: 0,
    genres = this.genre,
    actors = this.actress,
    overview = this.overview,
    runtime = this.runtime,
    production = this.production,
    awards = this.awards
)

fun MovieDto.toFavoriteEntity() = FavoritesMovieEntity(
    id = this.id ?: 0,
    title = this.title ?: "",
    poster = this.poster ?: "",
    year = this.year ?: 0,
    rating = 0.0,
    genre = this.genres ?: "",
    actress = actors,
    overview = overview,
    runtime = runtime,
    production = production,
    awards = awards
)