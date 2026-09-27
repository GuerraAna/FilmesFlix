package com.br.natanfc.filmesflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.br.natanfc.filmesflix.databinding.ActivityDetailBinding
import com.br.natanfc.filmesflix.domain.model.Movie

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie == null) {
            finish()
            return
        }

        val uiModel = DetailMapper.toUiModel(movie)
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
