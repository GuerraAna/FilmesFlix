package com.br.natanfc.filmesflix.data.mapper

import com.br.natanfc.filmesflix.data.local.MovieEntity
import com.br.natanfc.filmesflix.data.remote.MovieDetailDto
import com.br.natanfc.filmesflix.data.remote.MovieSummaryDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `summary dto maps to entity without detail fields`() {
        val entity = MovieSummaryDto(
            id = 1,
            title = "Interstellar",
            posterUrl = "https://example.com/interstellar.jpg"
        ).toEntity()

        assertEquals(1, entity.id)
        assertEquals("Interstellar", entity.title)
        assertEquals("https://example.com/interstellar.jpg", entity.posterUrl)
        assertNull(entity.overview)
        assertNull(entity.releaseDate)
    }

    @Test
    fun `detail dto maps all fields to entity`() {
        val entity = MovieDetailDto(
            id = 2,
            title = "Inception",
            posterUrl = "https://example.com/inception.jpg",
            overview = "Um ladrão invade sonhos.",
            releaseDate = "2010-07-16"
        ).toEntity()

        assertEquals(2, entity.id)
        assertEquals("Inception", entity.title)
        assertEquals("https://example.com/inception.jpg", entity.posterUrl)
        assertEquals("Um ladrão invade sonhos.", entity.overview)
        assertEquals("2010-07-16", entity.releaseDate)
    }

    @Test
    fun `entity maps to domain`() {
        val movie = MovieEntity(
            id = 3,
            title = "Dune",
            posterUrl = "https://example.com/dune.jpg",
            overview = "Paul Atreides em Arrakis.",
            releaseDate = "2021-10-22"
        ).toDomain()

        assertEquals(3, movie.id)
        assertEquals("Dune", movie.title)
        assertEquals("https://example.com/dune.jpg", movie.posterUrl)
        assertEquals("Paul Atreides em Arrakis.", movie.overview)
        assertEquals("2021-10-22", movie.releaseDate)
    }
}
