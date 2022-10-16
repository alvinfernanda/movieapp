package com.alvinfernanda.mobile.movieapp.data.network.repository

import com.alvinfernanda.mobile.movieapp.data.model.MovieResp
import com.alvinfernanda.mobile.movieapp.data.network.service.MovieService
import com.alvinfernanda.mobile.movieapp.domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieDataStore(
    private val service: MovieService,
    private val dispatcher: DispatcherProvider
) : MovieRepository {

    override suspend fun getMovies(page: Int?): Deferred<Response<MovieResp>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getMovies(page).await() }
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Int?): Deferred<Response<MovieResp>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getSimilarMovies(movieId).await() }
            }
        }
    }
}