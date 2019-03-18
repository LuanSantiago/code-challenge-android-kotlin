package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.TmdbRepository

class MovieDetailViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {

    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?>
        get() = _movie

    fun getMovie(id: Long){
        tmdbRepository.getMovie(id,
            success = {
                _movie.postValue(it)
            },
            error = {
                _movie.postValue(null)
            })
    }

}
