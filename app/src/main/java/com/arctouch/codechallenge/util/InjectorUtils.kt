package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.remote.datasource.TmdbRemoteDataSource
import com.arctouch.codechallenge.repository.TmdbRepository
import com.arctouch.codechallenge.viewmodel.factory.ViewModelFactory

class InjectorUtils {

    private fun provideRepository() : TmdbRepository {
        return TmdbRepository.getInstance(TmdbRemoteDataSource.getInstance())
    }

    fun provideViewModelFactory() : ViewModelFactory {
        val repository : TmdbRepository = provideRepository()
        return ViewModelFactory(repository)
    }

}