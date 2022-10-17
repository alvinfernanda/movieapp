package com.alvinfernanda.mobile.movieapp.data.dao

import androidx.room.*
import com.alvinfernanda.mobile.movieapp.data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE page = :page")
    fun getMovieList(page: Int?): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int?): Movie

    @Query("SELECT * FROM movie WHERE favorite = '1'")
    fun getFavouriteMovieList(): List<Movie>

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    fun getSimilarMovies(movieId: Int?): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

}