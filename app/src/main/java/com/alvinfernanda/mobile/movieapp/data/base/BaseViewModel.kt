package com.alvinfernanda.mobile.movieapp.data.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvinfernanda.mobile.movieapp.data.network.repository.MovieRepository
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.domain.dispatcher.DispatcherProvider
import com.alvinfernanda.mobile.movieapp.domain.room.DatabaseHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    val appDispatcher: DispatcherProvider by inject()
    val repository: MovieRepository by inject()
    val db: DatabaseHelper by inject()

    val context = application
    val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> get() = _loadingState
}