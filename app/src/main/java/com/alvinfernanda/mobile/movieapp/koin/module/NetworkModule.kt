package com.alvinfernanda.mobile.movieapp.koin.module

import com.alvinfernanda.mobile.movieapp.data.network.httpclient.coroutinesRestClient
import com.alvinfernanda.mobile.movieapp.data.network.httpclient.coroutinesServices
import com.alvinfernanda.mobile.movieapp.data.network.httpclient.httpClient
import com.alvinfernanda.mobile.movieapp.data.network.repository.MovieDataStore
import com.alvinfernanda.mobile.movieapp.data.network.repository.MovieRepository
import com.alvinfernanda.mobile.movieapp.domain.dispatcher.AppDispatcher
import com.alvinfernanda.mobile.movieapp.domain.dispatcher.DispatcherProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkModule = module {
    single<DispatcherProvider> { AppDispatcher() }
    single<MovieRepository> { MovieDataStore(get(), get()) }
    single { httpClient(androidApplication()) }
    single { coroutinesRestClient(get()) }
    single { coroutinesServices(get()) }
}