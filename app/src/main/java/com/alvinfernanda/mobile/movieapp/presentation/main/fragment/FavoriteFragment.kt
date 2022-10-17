package com.alvinfernanda.mobile.movieapp.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.FragmentFavoriteBinding
import com.alvinfernanda.mobile.movieapp.domain.callback.ShowMoreCallBack
import com.alvinfernanda.mobile.movieapp.external.extension.setVisibleIf
import com.alvinfernanda.mobile.movieapp.presentation.detail.view.DetailActivity
import com.alvinfernanda.mobile.movieapp.presentation.main.adapter.MoviesAdapter
import com.alvinfernanda.mobile.movieapp.presentation.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), MoviesAdapter.Listener, ShowMoreCallBack {

    private val viewModel: MainViewModel by viewModel()
    private var binding: FragmentFavoriteBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestData()
        initObserver()
    }

    private fun requestData() {
        // Get favorite movies from database
        viewModel.getFavoriteMovies()
    }

    private fun initObserver() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) setupFavoriteMovies(mutableListOf()) else setupFavoriteMovies(it)
        }
    }

    private fun setupFavoriteMovies(movies: MutableList<Movie>) {
        moviesAdapter = MoviesAdapter(this, this)
        moviesAdapter.setList(movies)
        binding?.rvFavoriteMovies?.adapter = moviesAdapter
        binding?.rvFavoriteMovies?.setHasFixedSize(false)
        binding?.tvEmpty?.setVisibleIf(movies.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(item: Movie) {
        DetailActivity.startActivityModel(context, item)
    }

    override fun onClickFavorite(item: Movie) {
        showMessage(getString(if (item.favorite) R.string.saved_favorite_movie else R.string.removed_favorite_movie))
        viewModel.updateFavorite(item)
        // Reques new data from db every time there is favorite change
        requestData()
    }

    private fun showMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMore() {}
}