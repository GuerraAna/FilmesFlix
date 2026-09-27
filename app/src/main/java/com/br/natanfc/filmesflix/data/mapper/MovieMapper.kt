package com.br.natanfc.filmesflix.data.mapper

import com.br.natanfc.filmesflix.data.remote.MovieDetailDto
import com.br.natanfc.filmesflix.data.remote.MovieSummaryDto
import com.br.natanfc.filmesflix.domain.model.Movie

fun MovieSummaryDto.toDomain(): Movie = Movie(
    id = id,
    titulo = title,
    imagem = posterUrl,
    descricao = null,
    dataLancamento = null
)

fun MovieDetailDto.toDomain(): Movie = Movie(
    id = id,
    titulo = title,
    imagem = posterUrl,
    descricao = overview,
    dataLancamento = releaseDate
)
