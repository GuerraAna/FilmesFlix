package com.br.natanfc.filmesflix.domain.repository

import com.br.natanfc.filmesflix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun observeMovies(): Flow<List<Movie>>

    fun observeMovie(id: Int): Flow<Movie?>

    suspend fun refreshMovies()

    suspend fun refreshMovie(id: Int)
}
