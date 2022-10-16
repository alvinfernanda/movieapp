package com.alvinfernanda.mobile.movieapp.presentation.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.base.BaseViewModel
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.external.extension.asLiveData
import com.alvinfernanda.mobile.movieapp.external.extension.notNull
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val _listMovies = MutableLiveData<MutableList<Movie>?>()
    val listMovies = _listMovies.asLiveData()

    fun getMovies(page: Int?) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = repository.getMovies(page).await()
                if (response.isSuccessful) {
                    response.body().notNull {
                        val result = response.body()?.results
                        result?.forEach { it.page = page }
                        _listMovies.postValue(result)
                    }
                } else {
                    _loadingState.postValue(LoadingState.error(context.getString(R.string.failed_fetch_movie)))
                }
            } catch (e: Exception) {
                _loadingState.postValue(LoadingState.error(context.getString(R.string.something_wrong)))
            }
        }
    }

    fun showMore(page: Int?) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = repository.getMovies(page).await()
                if (response.isSuccessful) {
                    response.body().notNull {
                        val results = response.body()?.results
                        results?.forEach { it.page = page }
                        val movies = _listMovies.value
                        movies?.let {
                            it.addAll(results!!)
                            _listMovies.postValue(it)
                        }
                    }
                } else {
                    _loadingState.postValue(LoadingState.error(context.getString(R.string.failed_fetch_movie)))
                }
            } catch (e: Exception) {
                _loadingState.postValue(LoadingState.error(context.getString(R.string.something_wrong)))
            }
        }
    }
}