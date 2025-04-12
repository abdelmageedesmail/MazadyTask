package com.abdelmageed.mazadytask.data.di

import android.content.Context
import androidx.room.Room
import com.abdelmageed.mazadytask.data.local.FavoriteMovieDao
import com.abdelmageed.mazadytask.data.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocaleModule {
    @Provides
    @Singleton
    fun provideImagesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MoviesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase): FavoriteMovieDao {
        return db.marvelDao()
    }
}