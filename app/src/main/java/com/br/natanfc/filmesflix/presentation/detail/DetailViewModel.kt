package com.br.natanfc.filmesflix.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.usecase.ObserveMovieByIdUseCase
import com.br.natanfc.filmesflix.domain.usecase.RefreshMovieByIdUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import com.br.natanfc.filmesflix.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeMovie: ObserveMovieByIdUseCase,
    private val refreshMovie: RefreshMovieByIdUseCase
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[Routes.ARG_MOVIE_ID])

    private val _state = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val state: StateFlow<UiState<Movie>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeMovie(movieId).collect { movie ->
                if (movie != null) _state.value = UiState.Success(movie)
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            runCatching { refreshMovie(movieId) }.onFailure { error ->
                if (_state.value !is UiState.Success) {
                    _state.value = UiState.Error(error.message ?: "Erro ao carregar detalhes")
                }
            }
        }
    }
}
