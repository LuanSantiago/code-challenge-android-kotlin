package com.arctouch.codechallenge.repository

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.remote.datasource.TmdbDataSource
import com.arctouch.codechallenge.remote.datasource.TmdbRemoteDataSource

class TmdbRepository(private val tmdbRemoteDataSource: TmdbDataSource) {


    fun getGenres(
        key: String,
        language: String
    ) {
        tmdbRemoteDataSource.getGenres(key,language,
            success = {
                Cache.cacheGenres(it)
            },
            error = {

            })
    }

    fun initializedPagedListBuilder(config: PagedList.Config):
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