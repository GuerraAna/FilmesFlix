package com.br.natanfc.filmesflix.domain.usecase

import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import javax.inject.Inject

class RefreshMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke() = movieRepository.refreshMovies()
}
