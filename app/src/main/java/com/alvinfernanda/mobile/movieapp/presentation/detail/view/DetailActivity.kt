package com.alvinfernanda.mobile.movieapp.presentation.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.ActivityDetailBinding
import com.alvinfernanda.mobile.movieapp.external.extension.convertToStringDate
import com.alvinfernanda.mobile.movieapp.external.extension.loadImage
import com.alvinfernanda.mobile.movieapp.external.extension.notNull

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initExtras()
    }

    private fun initExtras() {
        val data = intent.extras
        data.notNull { bundle ->
            val detail = bundle.getParcelable<Movie>(detail)
            detail.notNull {
                binding.ivPoster.loadImage(detail?.poster_path)
                binding.tvTitle.text = detail?.title
                binding.tvReleaseDate.text = getString(R.string.release_date).format(
                    convertToStringDate(detail?.release_date)
                )
                binding.tvDesc.text = detail?.overview
                bindFavorite(detail?.favorite!!)
                binding.ivFavorite.setOnClickListener {
                    detail.favorite = !detail.favorite
                    bindFavorite(detail.favorite)
                }
            }
        }
    }

    private fun bindFavorite(favorite: Boolean) {
        binding.ivFavorite.setImageResource(if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    companion object {
        const val detail = "detail"
        fun startActivityModel(context: Context?, movie: Movie) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(detail, movie)
            context?.startActivity(intent)
        }
    }

}