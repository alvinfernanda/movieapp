package com.alvinfernanda.mobile.movieapp.presentation.detail.module

import com.alvinfernanda.mobile.movieapp.presentation.detail.viewmodel.DetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(androidApplication()) }
}