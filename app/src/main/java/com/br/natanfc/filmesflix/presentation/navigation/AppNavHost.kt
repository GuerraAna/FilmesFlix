package com.br.natanfc.filmesflix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.br.natanfc.filmesflix.presentation.detail.MovieDetailScreen
import com.br.natanfc.filmesflix.presentation.main.MovieListScreen

object Routes {
    const val MOVIES = "movies"
    const val MOVIE_DETAIL = "movie/{movieId}"
    const val ARG_MOVIE_ID = "movieId"
    fun movieDetail(id: Int) = "movie/$id"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MOVIES) {
        composable(Routes.MOVIES) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetail(movieId))
                }
            )
        }
        composable(
            route = Routes.MOVIE_DETAIL,
            arguments = listOf(navArgument(Routes.ARG_MOVIE_ID) { type = NavType.IntType })
        ) {
            MovieDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
