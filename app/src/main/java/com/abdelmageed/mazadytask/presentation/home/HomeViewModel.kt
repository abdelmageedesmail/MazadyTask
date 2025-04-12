package com.abdelmageed.mazadytask.presentation.home

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.BaseResult
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import com.abdelmageed.mazadytask.domain.home.LocaleMoviesUseCase
import com.abdelmageed.mazadytask.domain.home.MoviesUseCase
import com.abdelmageed.mazadytask.extension.toFavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val localeMoviesUseCase: LocaleMoviesUseCase
) : ViewModel() {

    var isGridLayout: Boolean = false
    var recyclerViewState: Parcelable? = null
    private var genreMap: Map<Int, String> = emptyMap()
    private val _moviesFlow = MutableStateFlow<PagingData<ResultsItem>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<ResultsItem>> = _moviesFlow
    private val _favoritesFlow = MutableStateFlow<List<FavoritesMovieEntity>>(emptyList())
    val favoritesFlow: StateFlow<List<FavoritesMovieEntity>> = _favoritesFlow

    fun getMovies() {
        try {
            // Collect and update favorite movies
            viewModelScope.launch {
                localeMoviesUseCase.getFavorites().collectLatest {
                    _favoritesFlow.value = it
                }
            }

            viewModelScope.launch {
                moviesUseCase.getMoviesGears().collectLatest {
                    when (it) {
                        is BaseResult.Success -> {
                            genreMap =
                                it.data.genres?.associateBy({ it?.id ?: 0 }, { it?.name ?: "" })
                                    ?: emptyMap()
                            Log.d("GenreData", "$genreMap")
                        }

                        is BaseResult.Error -> Unit
                    }
                }
            }
            viewModelScope.launch {
                moviesUseCase().cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        // Combine movies with favorite status
                        _moviesFlow.value = pagingData.map { movie ->
                            val isFavorite = _favoritesFlow.value.any { it.id == movie.id }
                            movie.copy(
                                isFavoriteItem = isFavorite,
                                genres = getGenreNames(movie.genre_ids ?: emptyList())
                            )
                        }
                    }
            }
        } catch (e: IOException) {
            Log.e("NetworkError", "Network issue: ${e.message}")
        } catch (e: Exception) {
            Log.e("ApiError", "Unexpected error: ${e.message}")
        }
    }

    private fun getGenreNames(ids: List<Int?>): List<String> {
        return ids.mapNotNull { genreMap[it] }
    }

    fun toggleFavorite(movie: ResultsItem) {
        viewModelScope.launch {
            val isFav = localeMoviesUseCase.isFavorite(movie.id ?: 0).first()
            val updatedMovie = movie.copy(isFavoriteItem = !isFav)

            if (isFav) {
                localeMoviesUseCase.removeFromFavorites(movie.toFavoriteEntity())
            } else {
                localeMoviesUseCase.addToFavorites(movie.toFavoriteEntity())
            }

            _moviesFlow.value = _moviesFlow.value.map {
                if (it.id == movie.id) updatedMovie else it
            }
        }
    }
}