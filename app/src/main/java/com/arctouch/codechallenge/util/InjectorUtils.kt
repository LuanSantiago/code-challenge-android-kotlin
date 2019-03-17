package com.arctouch.codechallenge.util

import android.content.Context
import com.arctouch.codechallenge.remote.datasource.TmdbRemoteDataSource
import com.arctouch.codechallenge.repository.TmdbRepository
import com.arctouch.codechallenge.viewmodel.factory.ViewModelFactory

class InjectorUtils {

    private fun provideRepository(context : Context) : TmdbRepository {
        return TmdbRepository.getInstance(TmdbRemoteDataSource.getInstance())
    }

    fun provideViewModelFactory(context : Context) : ViewModelFactory {
        val repository : TmdbRepository = provideRepository(context.applicationContext)
        return ViewModelFactory(repository)
    }

}