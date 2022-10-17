package com.alvinfernanda.mobile.movieapp.presentation.detail.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.base.BaseViewModel
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.external.extension.asLiveData
import com.alvinfernanda.mobile.movieapp.external.extension.notNull
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {

    private val _listSimilarMovies = MutableLiveData<MutableList<Movie>?>()
    val listSimilarMovies = _listSimilarMovies.asLiveData()

    fun getSimilarMovies(movieId: Int?) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = repository.getSimilarMovies(movieId).await()
                if (response.isSuccessful) {
                    _loadingState.postValue(LoadingState.LOADED)
                    response.body().notNull {
                        // Get 5 similar movies from API
                        val results = response.body()?.results?.take(5)
                        val similarMovies = db.getSimilarMovies(movieId)
                        if (similarMovies.isEmpty()) {
                            // Insert movie id value for each item
                            results?.forEach { it.movie_id = movieId  }
                            db.insertMovieList(results!!)
                            _listSimilarMovies.postValue(results.toMutableList())
                        } else {
                            _listSimilarMovies.postValue(similarMovies.toMutableList())
                        }
                    }
                } else {
                    _loadingState.postValue(LoadingState.error(context.getString(R.string.failed_fetch_similar_movie)))
                }
            } catch (e: Exception) {
                _loadingState.postValue(LoadingState.error(context.getString(R.string.failed_fetch_similar_movie)))
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

}