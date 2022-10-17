package com.alvinfernanda.mobile.movieapp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alvinfernanda.mobile.movieapp.data.dao.MovieDao
import com.alvinfernanda.mobile.movieapp.data.model.Movie

@Database(
    version = 2, exportSchema = false, entities = [
        Movie::class
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}