package com.br.natanfc.filmesflix.presentation.main

import app.cash.turbine.test
import com.br.natanfc.filmesflix.FakeMovieRepository
import com.br.natanfc.filmesflix.MainDispatcherRule
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import com.br.natanfc.filmesflix.domain.usecase.ObserveMoviesUseCase
import com.br.natanfc.filmesflix.domain.usecase.RefreshMoviesUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movie = Movie(1, "Interstellar", "poster", "descricao", "2014-11-06")

    private fun viewModel(repository: MovieRepository) = MainViewModel(
        ObserveMoviesUseCase(repository),
        RefreshMoviesUseCase(repository)
    )

    @Test
    fun `state emits Loading then Success when movies arrive`() =
        runTest(mainDispatcherRule.dispatcher) {
            val sut = viewModel(FakeMovieRepository(moviesFlow = flowOf(listOf(movie))))

            sut.state.test {
                assertTrue(awaitItem() is UiState.Loading)
                advanceUntilIdle()
                assertEquals(listOf(movie), (awaitItem() as UiState.Success).data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `state emits Loading then Error when refresh fails with empty cache`() =
        runTest(mainDispatcherRule.dispatcher) {
            val sut = viewModel(
                FakeMovieRepository(refreshMoviesError = RuntimeException("no network"))
            )

            sut.state.test {
                assertTrue(awaitItem() is UiState.Loading)
                advanceUntilIdle()
                assertEquals("no network", (awaitItem() as UiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `state stays Success when refresh fails but cache has data`() =
        runTest(mainDispatcherRule.dispatcher) {
            val sut = viewModel(
                FakeMovieRepository(
                    moviesFlow = flowOf(listOf(movie)),
                    refreshMoviesError = RuntimeException("no network")
                )
            )

            sut.state.test {
                assertTrue(awaitItem() is UiState.Loading)
                advanceUntilIdle()
                assertEquals(listOf(movie), (awaitItem() as UiState.Success).data)
                expectNoEvents()
                cancelAndIgnoreRemainingEvents()
            }
        }
}
