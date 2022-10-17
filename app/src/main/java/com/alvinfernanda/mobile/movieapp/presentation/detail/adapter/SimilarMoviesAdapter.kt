package com.alvinfernanda.mobile.movieapp.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.ItemSimilarMovieBinding
import com.alvinfernanda.mobile.movieapp.external.extension.loadImage
import com.alvinfernanda.mobile.movieapp.external.extension.notNull

class SimilarMoviesAdapter(
    private val clickListener: Listener,
    private val items: MutableList<Movie>
) : RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSimilarMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Movie = items[position]
        holder.bind(item, clickListener)
    }

    class ViewHolder(val binding: ItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie, clickListener: Listener) {
            item.notNull {
                binding.ivPoster.loadImage(item.poster_path)
                binding.tvTitle.text = item.title
                binding.cardMovie.setOnClickListener {
                    clickListener.onClick(item)
                }
            }
        }
    }

    interface Listener {
        fun onClick(item: Movie)
    }
}