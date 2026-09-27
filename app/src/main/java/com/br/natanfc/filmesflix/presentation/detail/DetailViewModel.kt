package com.br.natanfc.filmesflix.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.usecase.GetMovieByIdUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import java.util.concurrent.Executors

class DetailViewModel(
    private val getMovieById: GetMovieByIdUseCase
) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    private val _movie = MutableLiveData<UiState<Movie>>()
    val movie: LiveData<UiState<Movie>> = _movie

    fun loadMovie(id: Int) {
        _movie.value = UiState.Loading
        executor.execute {
            try {
                _movie.postValue(UiState.Success(getMovieById.execute(id)))
            } catch (e: Exception) {
                _movie.postValue(UiState.Error(e.message ?: "Erro ao carregar detalhes"))
            }
        }
    }

    override fun onCleared() {
        executor.shutdown()
        super.onCleared()
    }
}
