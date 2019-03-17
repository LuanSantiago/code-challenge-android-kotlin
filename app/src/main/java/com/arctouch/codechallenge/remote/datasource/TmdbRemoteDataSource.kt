package com.arctouch.codechallenge.remote.datasource

import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.remote.api.TmdbApi
import com.arctouch.codechallenge.remote.request.TmdbRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TmdbRemoteDataSource : TmdbDataSource, PageKeyedDataSource<Long, Movie>() {

    private val FIRST_PAGE = 1

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Movie>
    ) {
        upcomingMovies(
            TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, FIRST_PAGE.toLong(), TmdbApi.DEFAULT_REGION,
            success = {
                val moviesWithGenres = it.results.map { movie ->
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                callback.onResult(moviesWithGenres, null, FIRST_PAGE.toLong() + 1)
            },
            error = {

            })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        upcomingMovies(TmdbApi.API_KEY,TmdbApi.DEFAULT_LANGUAGE,params.key,TmdbApi.DEFAULT_REGION,
            success = {
                val moviesWithGenres = it.results.map { movie ->
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                val key = (if (it.totalPages > params.key.toLong() ) params.key + 1 else null)?.toLong()
                callback.onResult(moviesWithGenres,key)
            },
            error = {

            })

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        success: (movies: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().upcomingMovies(key,language,page,region,
                success ={
                    success(it)
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
                    INSTANCE =
                        TmdbRemoteDataSource()
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