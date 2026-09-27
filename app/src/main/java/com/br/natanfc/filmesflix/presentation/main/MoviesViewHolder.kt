package com.br.natanfc.filmesflix.presentation.main

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.br.natanfc.filmesflix.databinding.MovieItemLayoutBinding
import com.br.natanfc.filmesflix.domain.model.Movie

class MoviesViewHolder(
    private val binding: MovieItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, onMovieClick: (Movie) -> Unit) {
        binding.movieTitle.text = movie.titulo
        binding.movieImage.load(movie.imagem)
        binding.root.setOnClickListener { onMovieClick(movie) }
    }
}
