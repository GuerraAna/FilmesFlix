package com.br.natanfc.filmesflix.data.repository

import com.br.natanfc.filmesflix.data.local.MovieDao
import com.br.natanfc.filmesflix.data.mapper.toDomain
import com.br.natanfc.filmesflix.data.mapper.toEntity
import com.br.natanfc.filmesflix.data.remote.MovieApi
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun observeMovies(): Flow<List<Movie>> =
        movieDao.observeMovies().map { entities -> entities.map { it.toDomain() } }

    override fun observeMovie(id: Int): Flow<Movie?> =
        movieDao.observeMovie(id).map { it?.toDomain() }

    override suspend fun refreshMovies() {
        movieDao.upsertSummaries(movieApi.getAllMovies().map { it.toEntity() })
    }

    override suspend fun refreshMovie(id: Int) {
        movieDao.upsert(movieApi.getMovieById(id).toEntity())
    }
}
