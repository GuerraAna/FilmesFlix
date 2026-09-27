package com.br.natanfc.filmesflix.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiClient {

    private const val BASE_URL = "https://raw.githubusercontent.com/"

    val movieApi: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}
