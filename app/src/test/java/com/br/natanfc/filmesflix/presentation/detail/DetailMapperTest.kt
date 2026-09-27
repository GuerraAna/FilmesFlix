package com.br.natanfc.filmesflix.presentation.detail

import com.br.natanfc.filmesflix.domain.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailMapperTest {

    private val movie = Movie(
        id = 1,
        titulo = "Vingadores: Guerra infinita",
        imagem = "https://example.com/poster.png",
        descricao = "Os vingadores enfrentam Thanos.",
        dataLancamento = "2018-04-26"
    )

    @Test
    fun `maps all movie fields to ui model`() {
        val uiModel = DetailMapper.toUiModel(movie)

        assertEquals("Vingadores: Guerra infinita", uiModel.title)
        assertEquals("https://example.com/poster.png", uiModel.posterUrl)
        assertEquals("26/04/2018", uiModel.releaseDate)
        assertEquals("Os vingadores enfrentam Thanos.", uiModel.description)
    }

    @Test
    fun `null description falls back to default text`() {
        val uiModel = DetailMapper.toUiModel(movie.copy(descricao = null))

        assertEquals(DetailMapper.DEFAULT_DESCRIPTION, uiModel.description)
    }

    @Test
    fun `blank description falls back to default text`() {
        val uiModel = DetailMapper.toUiModel(movie.copy(descricao = "  "))

        assertEquals(DetailMapper.DEFAULT_DESCRIPTION, uiModel.description)
    }

    @Test
    fun `null release date falls back to unavailable text`() {
        val uiModel = DetailMapper.toUiModel(movie.copy(dataLancamento = null))

        assertEquals(DateFormatter.DATE_UNAVAILABLE, uiModel.releaseDate)
    }
}
