package com.abdelmageed.mazadytask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritesMovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun marvelDao(): FavoriteMovieDao

    companion object {
        const val DATABASE_NAME = "movies_db"
    }

}