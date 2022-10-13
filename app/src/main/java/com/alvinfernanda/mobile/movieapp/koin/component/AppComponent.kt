package com.alvinfernanda.mobile.movieapp.koin.component

import com.alvinfernanda.mobile.movieapp.koin.module.appModule
import com.alvinfernanda.mobile.movieapp.koin.module.networkModule
import org.koin.core.module.Module

val appComponents: List<Module> = listOf(
    appModule,
    networkModule
)