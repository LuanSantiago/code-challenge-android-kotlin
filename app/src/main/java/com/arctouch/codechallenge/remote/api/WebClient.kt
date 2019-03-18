package com.arctouch.codechallenge.remote.api

import com.arctouch.codechallenge.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WebClient {

    companion object {

        private var client : TmdbApi? = null

        @JvmStatic
        fun getClient(): TmdbApi {
            if (client == null) {
                synchronized(TmdbApi::javaClass) {
                    client = WebClient()
                        .initClient()
                }
            }
            return client!!
        }

        @JvmStatic
        fun clearInstance() {
            client = null
        }
    }


    fun initClient() : TmdbApi? {

        val clientBuilder = OkHttpClient.Builder().addInterceptor { chain ->
            val url = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key",BuildConfig.API_KEY)
                .addQueryParameter("language",BuildConfig.DEFAULT_LANGUAGE)
                //.addQueryParameter("region",BuildConfig.DEFAULT_REGION)
                .build()

            val newRequest = chain.request().newBuilder()
                .url(url)
                .build()
            chain.proceed(newRequest)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuilder)
            .build()

        client = retrofit.create(TmdbApi::class.java)

        return client
    }

}