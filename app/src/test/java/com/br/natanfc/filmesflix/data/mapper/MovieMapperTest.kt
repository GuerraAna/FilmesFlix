package com.br.natanfc.filmesflix.data.mapper

import com.br.natanfc.filmesflix.data.remote.MovieDetailDto
import com.br.natanfc.filmesflix.data.remote.MovieSummaryDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `summary dto maps to domain without optional fields`() {
        val movie = MovieSummaryDto(
            id = 1,
            title = "Interstellar",
            posterUrl = "https://example.com/interstellar.jpg"
        ).toDomain()

        assertEquals(1, movie.id)
        assertEquals("Interstellar", movie.titulo)
        assertEquals("https://example.com/interstellar.jpg", movie.imagem)
        assertNull(movie.descricao)
        assertNull(movie.dataLancamento)
    }

    @Test
    fun `detail dto maps all fields to domain`() {
        val movie = MovieDetailDto(
            id = 2,
            title = "Inception",
            posterUrl = "https://example.com/inception.jpg",
            overview = "Um ladrão invade sonhos.",
            releaseDate = "2010-07-16"
        ).toDomain()

        assertEquals(2, movie.id)
        assertEquals("Inception", movie.titulo)
        assertEquals("https://example.com/inception.jpg", movie.imagem)
        assertEquals("Um ladrão invade sonhos.", movie.descricao)
        assertEquals("2010-07-16", movie.dataLancamento)
    }
}
