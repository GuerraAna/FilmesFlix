package com.br.natanfc.filmesflix.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import com.br.natanfc.filmesflix.domain.usecase.GetMovieByIdUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val movie = Movie(1, "Interstellar", "poster", "descricao", "2014-11-06")

    private fun viewModel(repository: MovieRepository) = DetailViewModel(GetMovieByIdUseCase(repository))

    private fun observeUntilTerminal(viewModel: DetailViewModel, id: Int): List<UiState<Movie>> {
        val latch = CountDownLatch(1)
        val states = mutableListOf<UiState<Movie>>()
        viewModel.movie.observeForever {
            states.add(it)
            if (it !is UiState.Loading) latch.countDown()
        }
        viewModel.loadMovie(id)
        assertTrue("Timed out waiting for terminal UiState", latch.await(5, TimeUnit.SECONDS))
        return states
    }

    @Test
    fun `loadMovie emits Loading then Success`() {
        val sut = viewModel(object : MovieRepository {
            override fun getAllMovies() = listOf(movie)
            override fun getMovie(id: Int) = movie
        })

        val states = observeUntilTerminal(sut, 1)

        assertTrue(states.first() is UiState.Loading)
        assertEquals(movie, (states.last() as UiState.Success).data)
    }

    @Test
    fun `loadMovie emits Loading then Error on failure`() {
        val sut = viewModel(object : MovieRepository {
            override fun getAllMovies() = listOf(movie)
            override fun getMovie(id: Int): Movie = throw RuntimeException("not found")
        })

        val states = observeUntilTerminal(sut, 99)

        assertTrue(states.first() is UiState.Loading)
        assertEquals("not found", (states.last() as UiState.Error).message)
    }
}
