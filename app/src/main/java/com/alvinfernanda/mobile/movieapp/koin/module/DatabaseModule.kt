package com.alvinfernanda.mobile.movieapp.koin.module

import com.alvinfernanda.mobile.movieapp.domain.room.DatabaseBuilder
import com.alvinfernanda.mobile.movieapp.domain.room.DatabaseHelper
import com.alvinfernanda.mobile.movieapp.domain.room.DatabaseHelperImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<DatabaseHelper> { DatabaseHelperImpl(DatabaseBuilder.getInstance(androidApplication())) }
}