package com.br.natanfc.filmesflix.domain.usecase

import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository

class GetMovieByIdUseCase(private val movieRepository: MovieRepository) {

    fun execute(id: Int): Movie = movieRepository.getMovie(id)
}
