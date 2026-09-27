package com.br.natanfc.filmesflix.data.remote

import com.br.natanfc.filmesflix.domain.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("natanfelipe/FilmesFlixJson/master/moviesList")
    fun getAllMovies(): Call<List<Movie>>
}
