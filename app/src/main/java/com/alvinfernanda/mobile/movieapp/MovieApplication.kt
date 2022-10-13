package com.alvinfernanda.mobile.movieapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alvinfernanda.mobile.movieapp.koin.component.appComponents
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@MovieApplication)
            modules(appComponents)
        }
    }

}