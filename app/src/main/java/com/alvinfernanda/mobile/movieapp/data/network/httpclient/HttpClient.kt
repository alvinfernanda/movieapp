package com.alvinfernanda.mobile.movieapp.data.network.httpclient

import android.app.Application
import com.alvinfernanda.mobile.movieapp.data.network.service.MovieService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun httpClient(mainApp: Application): OkHttpClient {
    val httpCacheDir = File(mainApp.cacheDir, "httpCache")
    val cache = Cache(httpCacheDir, 10 * 1024 * 1024)

    return OkHttpClient.Builder().apply {
        cache(cache)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)

        addInterceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (error: Exception) {
                val offlineRequest = chain.request().newBuilder()
                    .header(
                        "Cache-Control", "public, only-if-cached," +
                                "max-stale=" + 60 * 60 * 24
                    ).build()
                chain.proceed(offlineRequest)
            }
        }

    }.build()
}

fun coroutinesRestClient(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    val gson = GsonBuilder().setLenient().create()

    builder.apply {
        client(okHttpClient)
        baseUrl("https://developers.themoviedb.org/3")
        addCallAdapterFactory(CoroutineCallAdapterFactory())
        addConverterFactory(GsonConverterFactory.create(gson))
    }

    return builder.build()
}

fun coroutinesServices(restAdapter: Retrofit): MovieService {
    return restAdapter.create(MovieService::class.java)
}