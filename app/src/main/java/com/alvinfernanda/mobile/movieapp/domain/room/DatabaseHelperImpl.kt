package com.alvinfernanda.mobile.movieapp.domain.room

import com.alvinfernanda.mobile.movieapp.data.model.Movie

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getMovieList(page: Int?): List<Movie> = appDatabase.movieDao().getMovieList(page)
    override fun getMovieById(id: Int?): Movie = appDatabase.movieDao().getMovieById(id)
    override fun getFavouriteMovieList(): List<Movie> = appDatabase.movieDao().getFavouriteMovieList()
    override fun getSimilarMovies(movieId: Int?): List<Movie> = appDatabase.movieDao().getSimilarMovies(movieId)
    override fun insertMovieList(movies: List<Movie>) = appDatabase.movieDao().insertMovieList(movies)
    override fun updateMovie(movie: Movie) = appDatabase.movieDao().updateMovie(movie)
    override fun deleteMovie(movie: Movie) = appDatabase.movieDao().deleteMovie(movie)

}