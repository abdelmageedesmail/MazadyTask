package com.abdelmageed.mazadytask.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.domain.home.LocaleMoviesUseCase
import com.abdelmageed.mazadytask.domain.home.MoviesUseCase
import com.abdelmageed.mazadytask.extension.toFavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val localeMoviesUseCase: LocaleMoviesUseCase
) : ViewModel() {
    private val _moviesFlow = MutableStateFlow<PagingData<MoviesResponseItem>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<MoviesResponseItem>> = _moviesFlow
    private val _favoritesFlow = MutableStateFlow<List<FavoritesMovieEntity>>(emptyList())
    val favoritesFlow: StateFlow<List<FavoritesMovieEntity>> = _favoritesFlow

    init {
        // Collect and update favorite movies
        viewModelScope.launch {
            localeMoviesUseCase.getFavorites().collectLatest {
                _favoritesFlow.value = it
            }
        }

        viewModelScope.launch {
            moviesUseCase().cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    // Combine movies with favorite status
                    _moviesFlow.value = pagingData.map { movie ->
                        val isFavorite = _favoritesFlow.value.any { it.id == movie.id }
                        movie.copy(isFavoriteItem = isFavorite)
                    }
                }
        }
    }

    fun toggleFavorite(movie: MoviesResponseItem) {
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

    /*lateinit var moviesFlow: Flow<PagingData<MoviesResponseItem>>
        private set

    fun getMovies() {
        viewModelScope.launch {
            moviesFlow = moviesUseCase.invoke()
                .cachedIn(viewModelScope)

        }
    }

    val favorites = localeMoviesUseCase.getFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavorite(movie: FavoritesMovieEntity) {
        viewModelScope.launch {
            val isFav = localeMoviesUseCase.isFavorite(movie.id).first()
            if (isFav) localeMoviesUseCase.removeFromFavorites(movie)
            else localeMoviesUseCase.addToFavorites(movie)
        }
    }

    suspend fun checkFavorite(id: Int): Boolean {
        val isFav = localeMoviesUseCase.isFavorite(id).first()
        return isFav
    }
*/
}