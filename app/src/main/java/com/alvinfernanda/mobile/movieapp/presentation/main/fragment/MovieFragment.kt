package com.alvinfernanda.mobile.movieapp.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.FragmentMovieBinding
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.domain.callback.ShowMoreCallBack
import com.alvinfernanda.mobile.movieapp.external.extension.notNull
import com.alvinfernanda.mobile.movieapp.external.extension.setVisibleIf
import com.alvinfernanda.mobile.movieapp.presentation.detail.view.DetailActivity
import com.alvinfernanda.mobile.movieapp.presentation.main.adapter.MoviesAdapter
import com.alvinfernanda.mobile.movieapp.presentation.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), MoviesAdapter.Listener, ShowMoreCallBack {

    private val viewModel: MainViewModel by viewModel()
    private var binding: FragmentMovieBinding? = null

    private lateinit var moviesAdapter: MoviesAdapter
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestData()
        initObserver()
        setupAdapter()
    }

    private fun requestData() {
        viewModel.getMovies(page)
    }

    private fun initObserver() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it.status) {
                LoadingState.Status.RUNNING -> showLoading(true)
                LoadingState.Status.SUCCESS -> showLoading(false)
                LoadingState.Status.FAILED -> {
                    showLoading(false)
                    it.message.notNull { message ->
                        showMessage(message)
                    }
                }
            }
        }
        viewModel.listMovies.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) setupMovies(mutableListOf()) else setupMovies(it)
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding?.loader?.setVisibleIf(isShow)
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter(this, this)
        binding?.rvMovies?.adapter = moviesAdapter
        binding?.rvMovies?.setHasFixedSize(false)
    }

    private fun setupMovies(movies: MutableList<Movie>) {
        binding?.loader?.visibility = View.GONE
        moviesAdapter.setList(movies)
        binding?.tvEmpty?.setVisibleIf(movies.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        page = 1
    }

    override fun showMore() {
        binding?.loader?.visibility = View.VISIBLE
        page += 1
        viewModel.showMore(page)
    }

    override fun onClick(item: Movie) {
        DetailActivity.startActivityModel(context, item)
    }

    override fun onClickFavorite(item: Movie) {
        showMessage(getString(if (item.favorite) R.string.saved_favorite_movie else R.string.removed_favorite_movie))
        viewModel.updateFavorite(item)
    }

    private fun showMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}