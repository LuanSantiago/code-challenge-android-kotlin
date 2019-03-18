package com.arctouch.codechallenge.remote.request

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.remote.api.WebClient

class TmdbRequest {

    suspend fun getGenres(
        success: (response: GenreResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        try {
            val response = WebClient.getClient().genresAsync().await()
            if (response.isSuccessful) {
                response.body()?.let {
                    success(it)
                }
            } else
                error("error")
        } catch (e: Exception) {
            error("error")
        }
    }

    suspend fun upcomingMovies(
        page: Long,
        success: (response: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        try {
            val response = WebClient.getClient().upcomingMoviesAsync(page).await()
            if (response.isSuccessful) {
                response.body()?.let {
                    success(it)
                }
            } else
                error("error")
        } catch (e: Exception) {
            error("error")
        }
    }

    suspend fun getMovie(
        id: Long,
        success: (movie: Movie) -> Unit,
        error: (message: String) -> Unit
    ) {
        try {
            val response = WebClient.getClient().movieAsync(id).await()
            if (response.isSuccessful) {
                response.body()?.let {
                    success(it)
                }
            } else
                error("error")
        } catch (e: Exception) {
            error("error")
        }
    }
}