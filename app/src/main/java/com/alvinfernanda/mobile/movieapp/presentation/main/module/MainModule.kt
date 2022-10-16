package com.alvinfernanda.mobile.movieapp.presentation.main.module

import com.alvinfernanda.mobile.movieapp.presentation.main.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(androidApplication()) }
}