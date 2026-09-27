package com.br.natanfc.filmesflix.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.br.natanfc.filmesflix.data.remote.MovieApiClient
import com.br.natanfc.filmesflix.data.repository.MovieRepositoryImpl
import com.br.natanfc.filmesflix.databinding.ActivityMainBinding
import com.br.natanfc.filmesflix.domain.usecase.GetAllMoviesUseCase
import com.br.natanfc.filmesflix.presentation.UiState
import com.br.natanfc.filmesflix.presentation.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = MoviesAdapter { movie ->
        startActivity(DetailActivity.newIntent(this, movie))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesList.adapter = adapter

        val getAllMovies = GetAllMoviesUseCase(MovieRepositoryImpl(MovieApiClient.movieApi))
        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(getAllMovies))[MainViewModel::class.java]

        viewModel.movies.observe(this) { state ->
            when (state) {
                is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(state.data)
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.loadMovies()
        }
    }
}
