package com.arctouch.codechallenge.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.repository.TmdbRepository
import com.arctouch.codechallenge.viewmodel.HomeViewModel

class ViewModelFactory(private val tmdbRepository : TmdbRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass){
            when{
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(tmdbRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}