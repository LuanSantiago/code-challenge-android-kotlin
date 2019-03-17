package com.arctouch.codechallenge.remote

import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.remote.request.TmdbRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TmdbRemoteDataSource : TmdbDataSource {

    override fun getGenres(
        key: String,
        language: String,
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().getGenres(key,language,
                success ={
                    success(it.genres)
                },
                error = {
                    error(it)
                })
        }
    }

    override fun upcomingMovies(
        key: String,
        language: String,
        page: Long,
        region: String,
        success: (movies: List<Movie>) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().upcomingMovies(key,language,page,region,
                success ={
                    success(it.results)
                },
                error = {
                    error(it)
                })
        }
    }

    companion object {
        private var INSTANCE: TmdbRemoteDataSource? = null

        @JvmStatic
        fun getInstance(): TmdbRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(TmdbRemoteDataSource::javaClass) {
                    INSTANCE = TmdbRemoteDataSource()
                }
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun clearInstance() {
            INSTANCE = null
        }
    }
}