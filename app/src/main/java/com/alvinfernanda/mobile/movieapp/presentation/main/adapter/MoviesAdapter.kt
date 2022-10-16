package com.alvinfernanda.mobile.movieapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.ItemMovieBinding
import com.alvinfernanda.mobile.movieapp.domain.callback.ShowMoreCallBack
import com.alvinfernanda.mobile.movieapp.external.extension.loadImage
import com.alvinfernanda.mobile.movieapp.external.extension.notNull

class MoviesAdapter(
    private val clickListener: Listener,
    private val showMoreCallBack: ShowMoreCallBack
) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, showMoreCallBack)
    }

    private fun getLastItemPosition(): Int = itemCount - 1

    private fun isLastItem(position: Int): Boolean = position == getLastItemPosition()

    fun setList(items: MutableList<Movie>) =
        this.submitList(items.toList())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), isLastItem(position), clickListener)
    }

    class ViewHolder(
        private val binding: ItemMovieBinding,
        private val showMoreCallBack: ShowMoreCallBack
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie, lastItem: Boolean, clickListener: Listener) {
            item.notNull {
                binding.ivPoster.loadImage(item.poster_path)
                binding.tvTitle.text = item.title
                binding.tvDesc.text = item.overview
                bindFavorite(item.favorite)
                binding.ivFavorite.setOnClickListener {
                    item.favorite = !item.favorite
                    clickListener.onClickFavorite(item)
                    bindFavorite(item.favorite)
                }
                binding.ivPoster.setOnClickListener {
                    clickListener.onClick(item)
                }
                showMore(lastItem)
            }
        }

        private fun bindFavorite(favorite: Boolean) {
            binding.ivFavorite.setImageResource(if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        }

        private fun showMore(lastItem: Boolean) {
            if (lastItem) {
                showMoreCallBack.showMore()
            }
        }
    }

    interface Listener {
        fun onClick(item: Movie)
        fun onClickFavorite(item: Movie)
    }

    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean =
            oldItem == newItem
    }
}