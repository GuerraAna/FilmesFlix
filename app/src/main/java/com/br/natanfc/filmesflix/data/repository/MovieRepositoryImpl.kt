package com.br.natanfc.filmesflix.data.repository

import com.br.natanfc.filmesflix.data.remote.MovieApi
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import java.io.IOException

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override fun getAllMovies(): List<Movie> {
        val response = movieApi.getAllMovies().execute()
        if (response.isSuccessful) {
            return response.body().orEmpty()
        }
        throw IOException("Movies request failed with code ${response.code()}")
    }
}
