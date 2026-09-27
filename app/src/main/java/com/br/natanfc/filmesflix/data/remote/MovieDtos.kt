package com.br.natanfc.filmesflix.data.remote

data class MovieSummaryDto(
    val id: Int,
    val title: String,
    val posterUrl: String
)

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String?,
    val releaseDate: String?
)
