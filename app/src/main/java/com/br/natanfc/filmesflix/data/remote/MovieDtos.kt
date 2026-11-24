package com.br.natanfc.filmesflix.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class MovieSummaryDto(
    val id: Int,
    val title: String,
    val posterUrl: String
)

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String? = null,
    val releaseDate: String? = null
)
