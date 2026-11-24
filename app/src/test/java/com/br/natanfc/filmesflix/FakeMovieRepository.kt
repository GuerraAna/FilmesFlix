package com.br.natanfc.filmesflix

import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository(
    private val moviesFlow: Flow<List<Movie>> = flowOf(emptyList()),
    private val movieFlow: Flow<Movie?> = flowOf(null),
    private val refreshMoviesError: Throwable? = null,
    private val refreshMovieError: Throwable? = null
) : MovieRepository {

    override fun observeMovies(): Flow<List<Movie>> = moviesFlow

    override fun observeMovie(id: Int): Flow<Movie?> = movieFlow

    override suspend fun refreshMovies() {
        refreshMoviesError?.let { throw it }
    }

    override suspend fun refreshMovie(id: Int) {
        refreshMovieError?.let { throw it }
    }
}
