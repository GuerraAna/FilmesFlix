package com.br.natanfc.filmesflix.presentation.detail

import com.br.natanfc.filmesflix.domain.model.Movie

object DetailMapper {

    const val DEFAULT_DESCRIPTION = "Descrição indisponível"

    fun toUiModel(movie: Movie): DetailUiModel = DetailUiModel(
        title = movie.titulo,
        posterUrl = movie.imagem,
        releaseDate = DateFormatter.format(movie.dataLancamento),
        description = movie.descricao?.takeIf { it.isNotBlank() } ?: DEFAULT_DESCRIPTION
    )
}
