package com.br.natanfc.filmesflix.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.br.natanfc.filmesflix.FakeMovieRepository
import com.br.natanfc.filmesflix.MainDispatcherRule
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.repository.MovieRepository
import com.br.natanfc.filmesflix.domain.usecase.ObserveMovieByIdUseCase
import com.br.natanfc.filmesflix.domain.usecase.RefreshMovieByIdUseCase
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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movie = Movie(1, "Interstellar", "poster", "descricao", "2014-11-06")

    private fun viewModel(repository: MovieRepository) = DetailViewModel(
        SavedStateHandle(mapOf("movieId" to 1)),
        ObserveMovieByIdUseCase(repository),
        RefreshMovieByIdUseCase(repository)
    )

    @Test
    fun `state emits Loading then Success when movie arrives`() =
        runTest(mainDispatcherRule.dispatcher) {
            val sut = viewModel(FakeMovieRepository(movieFlow = flowOf(movie)))

            sut.state.test {
                assertTrue(awaitItem() is UiState.Loading)
                advanceUntilIdle()
                assertEquals(movie, (awaitItem() as UiState.Success).data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `state emits Loading then Error when refresh fails and cache is empty`() =
        runTest(mainDispatcherRule.dispatcher) {
            val sut = viewModel(
                FakeMovieRepository(refreshMovieError = RuntimeException("not found"))
            )

            sut.state.test {
                assertTrue(awaitItem() is UiState.Loading)
                advanceUntilIdle()
                assertEquals("not found", (awaitItem() as UiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
