package com.alvinfernanda.mobile.movieapp.data.network.repository

import com.alvinfernanda.mobile.movieapp.data.network.service.MovieService
import com.alvinfernanda.mobile.movieapp.domain.dispatcher.DispatcherProvider

class MovieDataStore(
    private val service: MovieService,
    private val dispatcher: DispatcherProvider
) : MovieRepository {
}