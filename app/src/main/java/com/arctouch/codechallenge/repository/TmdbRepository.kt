package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.remote.TmdbDataSource

class TmdbRepository(private val tmdbRemoteDataSource: TmdbDataSource): TmdbDataSource {


    override fun getGenres(
        key: String,
        language: String,
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    ) {
        tmdbRemoteDataSource.getGenres(key,language,
            success = {
                Cache.cacheGenres(it)
                success(it)
            },
            error = {
                error(it)
            })
    }

    override fun upcomingMovies(
        key: String,
        language: String,
        page: Long,
        region: String,
        success: (movies: List<Movie>) -> Unit,
        error: (message: String) -> Unit
    ) {
        tmdbRemoteDataSource.upcomingMovies(key,language,page,region,
            success = { movies ->
                val moviesWithGenres = movies.map { movie ->
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                success(moviesWithGenres)
            },
            error = {
                error(it)
            })
    }

    companion object {
        private var INSTANCE: TmdbRepository? = null
        @JvmStatic
        fun getInstance(tmdbRemoteDataSource: TmdbDataSource) =
            INSTANCE ?: synchronized(TmdbRepository::class.java) {
                INSTANCE ?: TmdbRepository(tmdbRemoteDataSource)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}