package com.br.natanfc.filmesflix.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.br.natanfc.filmesflix.presentation.navigation.AppNavHost
import com.br.natanfc.filmesflix.presentation.ui.theme.FilmesFlixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FilmesFlixTheme {
                AppNavHost()
            }
        }
    }
}
