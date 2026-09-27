package com.br.natanfc.filmesflix.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.br.natanfc.filmesflix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = MoviesAdapter { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesList.adapter = adapter
    }
}
