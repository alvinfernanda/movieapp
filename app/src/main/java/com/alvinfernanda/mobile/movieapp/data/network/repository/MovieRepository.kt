package com.alvinfernanda.mobile.movieapp.data.network.repository

import com.alvinfernanda.mobile.movieapp.data.model.MovieResp
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(page: Int?): Deferred<Response<MovieResp>>
    suspend fun getSimilarMovies(movieId: Int?): Deferred<Response<MovieResp>>
}