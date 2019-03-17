package com.arctouch.codechallenge.remote.request

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.remote.client.WebClient

class TmdbRequest {

    suspend fun getGenres(
        key: String,
        language: String,
        success: (response: GenreResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        try {
            val response = WebClient.getClient().genres(key, language).await()
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
        key: String,
        language: String,
        page: Long,
        region: String,
        success: (response: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        try {
            val response = WebClient.getClient()
                .upcomingMovies(key, language, page, region).await()
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