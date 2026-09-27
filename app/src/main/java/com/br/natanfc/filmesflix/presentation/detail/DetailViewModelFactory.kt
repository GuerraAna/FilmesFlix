package com.br.natanfc.filmesflix.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.natanfc.filmesflix.domain.usecase.GetMovieByIdUseCase

class DetailViewModelFactory(
    private val getMovieById: GetMovieByIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(getMovieById) as T
    }
}
