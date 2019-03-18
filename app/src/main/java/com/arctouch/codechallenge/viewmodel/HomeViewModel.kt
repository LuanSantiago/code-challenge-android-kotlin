package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.TmdbRepository

class HomeViewModel(tmdbRepository: TmdbRepository): ViewModel() {

    var postsLiveData  :LiveData<PagedList<Movie>>

    init {
        tmdbRepository.getGenres()
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = tmdbRepository.initializedPagedList(config).build()
    }

    fun getUpcomingMovies():LiveData<PagedList<Movie>> = postsLiveData

}
