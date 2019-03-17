package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.remote.api.TmdbApi
import com.arctouch.codechallenge.repository.TmdbRepository

class HomeViewModel(tmdbRepository: TmdbRepository): ViewModel() {

    var postsLiveData  :LiveData<PagedList<Movie>>

    init {
        tmdbRepository.getGenres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = tmdbRepository.initializedPagedListBuilder(config).build()
    }

    fun getUpcomingMovies():LiveData<PagedList<Movie>> = postsLiveData

}
