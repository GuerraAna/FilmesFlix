package com.br.natanfc.filmesflix.domain.usecase

import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(): Flow<List<Movie>> = movieRepository.observeMovies()
}
