package com.alvinfernanda.mobile.movieapp.presentation.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.base.BaseViewModel
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.domain.room.DatabaseHelper
import com.alvinfernanda.mobile.movieapp.external.extension.asLiveData
import com.alvinfernanda.mobile.movieapp.external.extension.notNull
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val db: DatabaseHelper by inject()
    private val _listMovies = MutableLiveData<MutableList<Movie>?>()
    private val _favoriteMovies = MutableLiveData<MutableList<Movie>?>()
    val listMovies = _listMovies.asLiveData()
    val favoriteMovies = _favoriteMovies.asLiveData()

    fun getMovies(page: Int?) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = repository.getMovies(page).await()
                if (response.isSuccessful) {
                    response.body().notNull {
                        val result = response.body()?.results
                        result?.forEach { it.page = page }
                        val movieDb = db.getMovieList(page).toMutableList()
                        if (movieDb.isEmpty()) {
                            _listMovies.postValue(result)
                            db.insertMovieList(result!!)
                        } else {
                            _listMovies.postValue(movieDb)
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

    fun showMore(page: Int?) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = repository.getMovies(page).await()
                if (response.isSuccessful) {
                    response.body().notNull {
                        val results = response.body()?.results
                        results?.forEach { it.page = page }
                        val movieDb = db.getMovieList(page).toMutableList()
                        val movies = _listMovies.value
                        movies?.let {
                            if (movieDb.isEmpty()) {
                                it.addAll(results!!)
                                db.insertMovieList(results)
                            } else {
                                it.addAll(movieDb)
                            }
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

    fun updateFavorite(movie: Movie) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                db.updateMovie(movie)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                val data = db.getFavouriteMovieList()
                _favoriteMovies.postValue(data.toMutableList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}