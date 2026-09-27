package com.br.natanfc.filmesflix.data.repository

import com.br.natanfc.filmesflix.data.mapper.toDomain
import com.br.natanfc.filmesflix.data.remote.MovieApi
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import java.io.IOException

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override fun getAllMovies(): List<Movie> {
        val response = movieApi.getAllMovies().execute()
        if (response.isSuccessful) {
            return response.body().orEmpty().map { it.toDomain() }
        }
        throw IOException("Movies request failed with code ${response.code()}")
    }

    override fun getMovie(id: Int): Movie {
        val response = movieApi.getMovieById(id).execute()
        if (response.isSuccessful) {
            return response.body()?.toDomain()
                ?: throw IOException("Movie $id returned empty body")
        }
        throw IOException("Movie request failed with code ${response.code()}")
    }
}
