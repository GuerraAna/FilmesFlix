package com.br.natanfc.filmesflix.domain.repository

import com.br.natanfc.filmesflix.domain.model.Movie

interface MovieRepository {

    fun getAllMovies(): List<Movie>
}
