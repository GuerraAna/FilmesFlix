package com.br.natanfc.filmesflix.domain.usecase

import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository

class GetAllMoviesUseCase(private val movieRepository: MovieRepository) {

    fun execute(): List<Movie> = movieRepository.getAllMovies()
}
