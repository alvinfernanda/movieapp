package com.alvinfernanda.mobile.movieapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alvinfernanda.mobile.movieapp.external.extension.debugMode
import com.alvinfernanda.mobile.movieapp.koin.component.appComponents
import com.gu.toolargetool.TooLargeTool
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializeKoin()

        debugMode {
            TooLargeTool.startLogging(this)
        }
    }

    private fun initializeKoin() {
        startKoin {
            debugMode { androidLogger(Level.ERROR) }
            androidContext(this@MovieApplication)
            modules(appComponents)
        }
    }

}