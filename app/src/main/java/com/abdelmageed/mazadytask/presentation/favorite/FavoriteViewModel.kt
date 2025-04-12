package com.abdelmageed.mazadytask.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelmageed.mazadytask.data.local.FavoritesMovieEntity
import com.abdelmageed.mazadytask.domain.home.LocaleMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val localeMoviesUseCase: LocaleMoviesUseCase) :
    ViewModel() {

    private val _favoritesFlow = MutableStateFlow<List<FavoritesMovieEntity>>(emptyList())
    val favoritesFlow: StateFlow<List<FavoritesMovieEntity>> = _favoritesFlow


    fun getFavorites() {
        viewModelScope.launch {
            localeMoviesUseCase.getFavorites().collectLatest {
                _favoritesFlow.value = it
            }
        }
    }

    fun toggleFavorite(movie: FavoritesMovieEntity) {
        viewModelScope.launch {
            val isFav = localeMoviesUseCase.isFavorite(movie.id).first()
            if (isFav) {
                localeMoviesUseCase.removeFromFavorites(movie)
            }
        }
    }

}