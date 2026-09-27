package com.br.natanfc.filmesflix.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.usecase.GetAllMoviesUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import java.util.concurrent.Executors

class MainViewModel(
    private val getAllMovies: GetAllMoviesUseCase
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    private val _movies = MutableLiveData<UiState<List<Movie>>>()
    val movies: LiveData<UiState<List<Movie>>> = _movies

    fun loadMovies() {
        _movies.value = UiState.Loading
        executor.execute {
            try {
                _movies.postValue(UiState.Success(getAllMovies.execute()))
            } catch (e: Exception) {
                _movies.postValue(UiState.Error(e.message ?: "Erro ao carregar filmes"))
            }
        }
    }

    override fun onCleared() {
        executor.shutdown()
        super.onCleared()
    }
}
