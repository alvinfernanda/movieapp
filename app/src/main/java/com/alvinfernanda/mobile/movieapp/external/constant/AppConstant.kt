package com.alvinfernanda.mobile.movieapp.external.constant

object AppConstant {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val DATABASE_NAME = "movie_db"

    object ENDPOINT {
        const val NOW_PLAYING = "movie/now_playing"
        const val SIMILAR = "movie/{movie_id}/similar"
    }
}