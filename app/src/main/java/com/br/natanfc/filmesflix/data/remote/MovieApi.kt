package com.br.natanfc.filmesflix.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("v1/movies")
    suspend fun getAllMovies(): List<MovieSummaryDto>

    @GET("v1/movies/{id}")
    suspend fun getMovieById(@Path("id") id: Int): MovieDetailDto
}
