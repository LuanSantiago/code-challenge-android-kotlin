package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.remote.api.TmdbApi
import com.arctouch.codechallenge.repository.TmdbRepository

class HomeViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        getMovies()
    }

    private fun getMovies(){
        tmdbRepository.getGenres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE,
            success = {
                tmdbRepository.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION,
                    success = {
                        _movies.postValue(it)
                    },
                    error = {

                    })
            },
            error = {

            })
    }

}
