package com.br.natanfc.filmesflix.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("v1/movies")
    fun getAllMovies(): Call<List<MovieSummaryDto>>

    @GET("v1/movies/{id}")
    fun getMovieById(@Path("id") id: Int): Call<MovieDetailDto>
}
