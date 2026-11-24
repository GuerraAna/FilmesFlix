package com.br.natanfc.filmesflix.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.usecase.ObserveMoviesUseCase
import com.br.natanfc.filmesflix.domain.usecase.RefreshMoviesUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeMovies: ObserveMoviesUseCase,
    private val refreshMovies: RefreshMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Movie>>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeMovies().collect { movies ->
                if (movies.isNotEmpty()) {
                    _state.update { UiState.Success(movies) }
                }
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            if (_state.value !is UiState.Success) _state.value = UiState.Loading
            runCatching { refreshMovies() }.onFailure { error ->
                if (_state.value !is UiState.Success) {
                    _state.value = UiState.Error(error.message ?: "Erro ao carregar filmes")
                }
            }
        }
    }
}
