package com.alvinfernanda.mobile.movieapp.domain.room

import android.content.Context
import androidx.room.Room
import com.alvinfernanda.mobile.movieapp.external.constant.AppConstant

object DatabaseBuilder {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildRoomDB(context).also { instance = it }
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppConstant.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
}