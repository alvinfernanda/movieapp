package com.alvinfernanda.mobile.movieapp.koin.component

import com.alvinfernanda.mobile.movieapp.koin.module.databaseModule
import com.alvinfernanda.mobile.movieapp.koin.module.networkModule
import com.alvinfernanda.mobile.movieapp.presentation.detail.module.detailModule
import com.alvinfernanda.mobile.movieapp.presentation.main.module.mainModule
import org.koin.core.module.Module

val appComponents: List<Module> = listOf(
    // Base Module
    networkModule,
    databaseModule,

    // Feature Module
    mainModule,
    detailModule
)