package com.alvinfernanda.mobile.movieapp.presentation.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.ActivityDetailBinding
import com.alvinfernanda.mobile.movieapp.domain.LoadingState
import com.alvinfernanda.mobile.movieapp.external.extension.convertToStringDate
import com.alvinfernanda.mobile.movieapp.external.extension.loadImage
import com.alvinfernanda.mobile.movieapp.external.extension.notNull
import com.alvinfernanda.mobile.movieapp.external.extension.setVisibleIf
import com.alvinfernanda.mobile.movieapp.presentation.detail.adapter.SimilarMoviesAdapter
import com.alvinfernanda.mobile.movieapp.presentation.detail.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(), SimilarMoviesAdapter.Listener {

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initExtras()
        initObserver()
    }

    // Get data from intent
    private fun initExtras() {
        val data = intent.extras
        // If data not empty
        data.notNull { bundle ->
            val detail = bundle.getParcelable<Movie>(detail)
            detail.notNull {
                viewModel.getSimilarMovies(detail?.id)
                binding.ivPoster.loadImage(detail?.poster_path)
                binding.tvTitle.text = detail?.title
                binding.tvReleaseDate.text = getString(R.string.release_date).format(
                    convertToStringDate(detail?.release_date)
                )
                binding.tvDesc.text = detail?.overview
                bindFavorite(detail?.favorite!!)
                binding.ivFavorite.setOnClickListener {
                    detail.favorite = !detail.favorite
                    viewModel.updateFavorite(detail)
                    bindFavorite(detail.favorite)
                    showMessage(getString(if (detail.favorite) R.string.saved_favorite_movie else R.string.removed_favorite_movie))
                }
            }
        }
    }

    private fun initObserver() {
        // Observe loading state from view model
        viewModel.loadingState.observe(this) {
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
        // Observe list similar movies value from view model then insert it into adapter
        viewModel.listSimilarMovies.observe(this) {
            if (it.isNullOrEmpty()) setupSimilarMovies(mutableListOf()) else setupSimilarMovies(it)
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.layoutProgressbar.setVisibleIf(isShow)
    }

    private fun setupSimilarMovies(movies: MutableList<Movie>) {
        similarMoviesAdapter = SimilarMoviesAdapter(this, movies)
        binding.rvSimilarMovies.adapter = similarMoviesAdapter
        binding.rvSimilarMovies.setHasFixedSize(false)
        // Hide similar movie title if similar movies value is empty
        binding.tvSimilar.setVisibleIf(movies.isNotEmpty())
    }

    private fun bindFavorite(favorite: Boolean) {
        binding.ivFavorite.setImageResource(if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    private fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val detail = "detail"
        fun startActivityModel(context: Context?, movie: Movie) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(detail, movie)
            context?.startActivity(intent)
        }
    }

    override fun onClick(item: Movie) {
        startActivityModel(this, item)
    }

}