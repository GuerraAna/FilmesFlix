package com.br.natanfc.filmesflix.data.mapper

import com.br.natanfc.filmesflix.data.local.MovieEntity
import com.br.natanfc.filmesflix.data.remote.MovieDetailDto
import com.br.natanfc.filmesflix.data.remote.MovieSummaryDto
import com.br.natanfc.filmesflix.domain.model.Movie

fun MovieSummaryDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview = null,
    releaseDate = null
)

fun MovieDetailDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview = overview,
    releaseDate = releaseDate
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = posterUrl,
    overview = overview,
    releaseDate = releaseDate
)
