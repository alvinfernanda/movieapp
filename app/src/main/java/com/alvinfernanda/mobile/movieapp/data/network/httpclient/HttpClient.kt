package com.alvinfernanda.mobile.movieapp.data.network.httpclient

import android.app.Application
import com.alvinfernanda.mobile.movieapp.BuildConfig
import com.alvinfernanda.mobile.movieapp.data.network.service.MovieService
import com.alvinfernanda.mobile.movieapp.external.constant.AppConstant
import com.alvinfernanda.mobile.movieapp.external.extension.debugMode
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun httpClient(mainApp: Application): OkHttpClient {
    val httpCacheDir = File(mainApp.cacheDir, "httpCache")
    val cache = Cache(httpCacheDir, 10 * 1024 * 1024)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder().apply {
        cache(cache)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)

        debugMode {
            addInterceptor(loggingInterceptor)
        }

        addInterceptor { chain ->
            try {
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                // add TheMovieDB api key automatically every requests.
                val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()

                val requestBuilder = originalRequest.newBuilder().url(url)
                val request = requestBuilder.build()

                chain.proceed(request)
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
        baseUrl(AppConstant.BASE_URL)
        addCallAdapterFactory(CoroutineCallAdapterFactory())
        addConverterFactory(GsonConverterFactory.create(gson))
    }

    return builder.build()
}

fun coroutinesServices(restAdapter: Retrofit): MovieService {
    return restAdapter.create(MovieService::class.java)
}