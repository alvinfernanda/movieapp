package com.alvinfernanda.mobile.movieapp.koin.component

import com.alvinfernanda.mobile.movieapp.koin.module.appModule
import com.alvinfernanda.mobile.movieapp.koin.module.networkModule
import com.alvinfernanda.mobile.movieapp.presentation.main.module.mainModule
import org.koin.core.module.Module

val appComponents: List<Module> = listOf(
    appModule,
    networkModule,
    mainModule,
)