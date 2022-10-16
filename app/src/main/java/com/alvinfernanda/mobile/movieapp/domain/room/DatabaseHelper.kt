package com.alvinfernanda.mobile.movieapp.domain.room

import com.alvinfernanda.mobile.movieapp.data.model.Movie

interface DatabaseHelper {

    fun getMovieList(page: Int?): List<Movie>
    fun getMovieById(id: Int?): Movie
    fun getFavouriteMovieList(): List<Movie>
    fun insertMovieList(movies: List<Movie>)
    fun updateMovie(movie: Movie)
    fun deleteMovie(movie: Movie)

}