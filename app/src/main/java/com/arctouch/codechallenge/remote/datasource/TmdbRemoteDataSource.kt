package com.arctouch.codechallenge.remote.datasource

import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.remote.request.TmdbRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TmdbRemoteDataSource : TmdbDataSource, PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Movie>
    ) {
        upcomingMovies(FIRST_PAGE.toLong(),
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
        upcomingMovies(params.key,
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
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().getGenres(
                success = {
                    success(it.genres)
                },
                error = {
                    error(it)
                })
        }
    }

    override fun upcomingMovies(
        page: Long,
        success: (movies: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().upcomingMovies(page,
                success ={
                    success(it)
                },
                error = {
                    error(it)
                })
        }
    }

    override fun getMovie(
        id: Long,
        success: (movie: Movie) -> Unit,
        error: (message: String) -> Unit
    ) {
        GlobalScope.launch {
            TmdbRequest().getMovie(id,
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
        private const val FIRST_PAGE = 1

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