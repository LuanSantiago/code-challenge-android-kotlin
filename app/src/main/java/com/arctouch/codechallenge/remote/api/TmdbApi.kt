package com.arctouch.codechallenge.remote.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
    }

    @GET("genre/movie/list")
    fun genresAsync(): Deferred<Response<GenreResponse>>

    @GET("movie/upcoming")
    fun upcomingMoviesAsync(@Query("page") page: Long): Deferred<Response<UpcomingMoviesResponse>>

    @GET("movie/{id}")
    fun movieAsync(@Path("id") id: Long):Deferred<Response<Movie>>
}
