package com.alvinfernanda.mobile.movieapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.alvinfernanda.mobile.movieapp.data.dao.MovieDao
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.domain.room.AppDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class DatabaseUnitTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun movie(): Movie {
        return Movie(
            id = 1,
            poster_path = "",
            title = "tes",
            page = 1,
            overview = "",
            release_date = "",
        )
    }

    @Test
    @Throws(IOException::class)
    fun insertAndReadMovie() {
        val movie = movie()
        movieDao.insertMovieList(listOf(movie))
        val movieDb = movieDao.getMovieList(1)
        assertThat(movieDb.contains(movie)).isTrue()
    }

    @Test
    @Throws(IOException::class)
    fun updateAndReadMovie() {
        val movie = movie()
        movieDao.insertMovieList(listOf(movie))
        val movieById = movieDao.getMovieById(1)
        movieById.page = 2
        movieDao.updateMovie(movieById)
        assertThat(movieById.page).isEqualTo(2)
    }

    @Test
    @Throws(IOException::class)
    fun deleteAndReadMovie() {
        val movie = movie()
        movieDao.insertMovieList(listOf(movie))
        val movieById = movieDao.getMovieById(1)
        movieDao.deleteMovie(movieById)
        val movieDb = movieDao.getMovieList(1)
        assertThat(movieDb).isEmpty()
    }
}