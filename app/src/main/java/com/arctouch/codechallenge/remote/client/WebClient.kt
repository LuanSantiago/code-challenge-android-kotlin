package com.arctouch.codechallenge.remote.client

import com.arctouch.codechallenge.remote.api.TmdbApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WebClient {

    companion object {

        private var client : TmdbApi? = null


        @JvmStatic
        fun getClient(): TmdbApi {
            if (client == null) {
                synchronized(TmdbApi::javaClass) {
                    client = WebClient().initClientLogin()
                }
            }
            return client!!
        }

        @JvmStatic
        fun clearInstance() {
            client = null
        }
    }


    fun initClientLogin() : TmdbApi? {

        val retrofit = Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        client = retrofit.create(TmdbApi::class.java)

        return client
    }

}