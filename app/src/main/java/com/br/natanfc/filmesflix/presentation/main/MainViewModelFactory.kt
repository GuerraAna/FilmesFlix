package com.br.natanfc.filmesflix.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.natanfc.filmesflix.domain.usecase.GetAllMoviesUseCase

class MainViewModelFactory(
    private val getAllMovies: GetAllMoviesUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getAllMovies) as T
    }
}
