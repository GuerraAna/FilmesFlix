package com.br.natanfc.filmesflix.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import com.br.natanfc.filmesflix.domain.usecase.GetAllMoviesUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val movie = Movie(1, "Interstellar", "poster", "descricao", "2014-11-06")

    private fun viewModel(repository: MovieRepository) = MainViewModel(GetAllMoviesUseCase(repository))

    private fun observeUntilTerminal(viewModel: MainViewModel): List<UiState<List<Movie>>> {
        val latch = CountDownLatch(1)
        val states = mutableListOf<UiState<List<Movie>>>()
        viewModel.movies.observeForever {
            states.add(it)
            if (it !is UiState.Loading) latch.countDown()
        }
        viewModel.loadMovies()
        assertTrue("Timed out waiting for terminal UiState", latch.await(5, TimeUnit.SECONDS))
        return states
    }

    @Test
    fun `loadMovies emits Loading then Success`() {
        val sut = viewModel(object : MovieRepository {
            override fun getAllMovies() = listOf(movie)
            override fun getMovie(id: Int) = movie
        })

        val states = observeUntilTerminal(sut)

        assertTrue(states.first() is UiState.Loading)
        assertEquals(listOf(movie), (states.last() as UiState.Success).data)
    }

    @Test
    fun `loadMovies emits Loading then Error on failure`() {
        val sut = viewModel(object : MovieRepository {
            override fun getAllMovies(): List<Movie> = throw RuntimeException("no network")
            override fun getMovie(id: Int) = movie
        })

        val states = observeUntilTerminal(sut)

        assertTrue(states.first() is UiState.Loading)
        assertEquals("no network", (states.last() as UiState.Error).message)
    }
}
