package com.arctouch.codechallenge.remote.datasource

import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.UpcomingMoviesResponse

interface TmdbDataSource {

    fun getGenres(
        key: String,
        language: String,
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    )

    fun upcomingMovies(
        key: String,
        language: String,
        page: Long,
        region: String,
        success: (movies: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    )

}