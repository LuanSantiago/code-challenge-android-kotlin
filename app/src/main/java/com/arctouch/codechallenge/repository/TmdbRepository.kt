package com.arctouch.codechallenge.repository

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.remote.datasource.TmdbDataSource
import com.arctouch.codechallenge.remote.datasource.TmdbRemoteDataSource

class TmdbRepository(private val tmdbRemoteDataSource: TmdbDataSource): TmdbDataSource {


    override fun getGenres(
        success: (genres: List<Genre>) -> Unit,
        error: (message: String) -> Unit
    ) {
        tmdbRemoteDataSource.getGenres(
            success = {
                success(it)
            },
            error = {
                error(error)
            })
    }

    override fun upcomingMovies(
        page: Long,
        success: (movies: UpcomingMoviesResponse) -> Unit,
        error: (message: String) -> Unit
    ) {
        tmdbRemoteDataSource.upcomingMovies(page,
            success ={
                success(it)
            }, error = {
                error(error)
            })
    }

    override fun getMovie(
        id:Long,
        success: (movie: Movie) -> Unit,
        error: (message: String) -> Unit){
        tmdbRemoteDataSource.getMovie(id,
            success = {
                success(it)
            },
            error = {
                error(it)
            })
    }

    fun initializedPagedList(config: PagedList.Config):
            LivePagedListBuilder<Long, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Long, Movie>() {
            override fun create(): DataSource<Long, Movie> {
                return TmdbRemoteDataSource()
            }
        }
        return LivePagedListBuilder<Long, Movie>(dataSourceFactory, config)
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