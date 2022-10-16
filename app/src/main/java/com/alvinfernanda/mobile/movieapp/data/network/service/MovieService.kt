package com.alvinfernanda.mobile.movieapp.data.network.service

import com.alvinfernanda.mobile.movieapp.data.model.MovieResp
import com.alvinfernanda.mobile.movieapp.external.constant.AppConstant
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(AppConstant.ENDPOINT.NOW_PLAYING)
    fun getMovies(@Query("page") page: Int?): Deferred<Response<MovieResp>>

    @GET(AppConstant.ENDPOINT.SIMILAR)
    fun getSimilarMovies(@Path("movie_id") movieId: Int?): Deferred<Response<MovieResp>>

}