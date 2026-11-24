package com.br.natanfc.filmesflix.presentation.detail

import com.br.natanfc.filmesflix.domain.model.Movie

object DetailMapper {

    const val DEFAULT_DESCRIPTION = "Descrição indisponível"

    fun toUiModel(movie: Movie): DetailUiModel = DetailUiModel(
        title = movie.title,
        posterUrl = movie.posterUrl,
        releaseDate = DateFormatter.format(movie.releaseDate),
        description = movie.overview?.takeIf { it.isNotBlank() } ?: DEFAULT_DESCRIPTION
    )
}
