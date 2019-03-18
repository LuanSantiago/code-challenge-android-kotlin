package com.arctouch.codechallenge.remote.datasource

import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse

interface TmdbDataSource {

    fun getGenres(
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    )

    fun upcomingMovies(
        page: Long,
        success: (movies: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    )

    fun getMovie(
        id:Long,
        success: (movie: Movie) -> Unit,
        error: (message: String) -> Unit
    )

}