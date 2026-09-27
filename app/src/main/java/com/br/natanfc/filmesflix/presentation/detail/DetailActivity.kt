package com.br.natanfc.filmesflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.br.natanfc.filmesflix.data.remote.MovieApiClient
import com.br.natanfc.filmesflix.data.repository.MovieRepositoryImpl
import com.br.natanfc.filmesflix.databinding.ActivityDetailBinding
import com.br.natanfc.filmesflix.domain.model.Movie
import com.br.natanfc.filmesflix.domain.usecase.GetMovieByIdUseCase
import com.br.natanfc.filmesflix.presentation.UiState

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie == null) {
            finish()
            return
        }

        binding.moviePoster.load(movie.imagem)
        binding.movieTitle.text = movie.titulo

        val getMovieById = GetMovieByIdUseCase(MovieRepositoryImpl(MovieApiClient.movieApi))
        val viewModel = ViewModelProvider(this, DetailViewModelFactory(getMovieById))
            .get(DetailViewModel::class.java)

        viewModel.movie.observe(this) { state ->
            when (state) {
                is UiState.Loading -> Unit
                is UiState.Success -> render(DetailMapper.toUiModel(state.data))
                is UiState.Error -> {
                    render(DetailMapper.toUiModel(movie))
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.loadMovie(movie.id)
    }

    private fun render(uiModel: DetailUiModel) {
        binding.moviePoster.load(uiModel.posterUrl)
        binding.movieTitle.text = uiModel.title
        binding.movieReleaseDate.text = uiModel.releaseDate
        binding.movieDescription.text = uiModel.description
    }

    companion object {
        private const val EXTRA_MOVIE = "com.br.natanfc.filmesflix.EXTRA_MOVIE"

        fun newIntent(context: Context, movie: Movie): Intent =
            Intent(context, DetailActivity::class.java).putExtra(EXTRA_MOVIE, movie)
    }
}
