package com.alvinfernanda.mobile.movieapp.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvinfernanda.mobile.movieapp.data.model.Movie
import com.alvinfernanda.mobile.movieapp.databinding.FragmentMovieBinding
import com.alvinfernanda.mobile.movieapp.domain.callback.ShowMoreCallBack
import com.alvinfernanda.mobile.movieapp.presentation.detail.view.DetailActivity
import com.alvinfernanda.mobile.movieapp.presentation.main.adapter.MoviesAdapter

class MovieFragment : Fragment(), MoviesAdapter.Listener, ShowMoreCallBack {

    private var binding: FragmentMovieBinding? = null

    private lateinit var moviesAdapter: MoviesAdapter
    private val moviesData = mutableListOf(
        Movie(
            id = 1,
            poster_path = "/pHkKbIRoCe7zIFvqan9LFSaQAde.jpg",
            title = "Orphan: First Kill",
            page = 1,
            overview = "After escaping from an Estonian psychiatric facility, Leena Klammer travels to America by impersonating Esther, the missing daughter of a wealthy family. But when her mask starts to slip, she is put against a mother who will protect her family from the murderous “child” at any cost.",
            release_date = "2022-07-27",
        ),
        Movie(
            id = 2,
            poster_path = "/spCAxD99U1A6jsiePFoqdEcY0dG.jpg",
            title = "Fall",
            page = 1,
            overview = "For best friends Becky and Hunter, life is all about conquering fears and pushing limits. But after they climb 2,000 feet to the top of a remote, abandoned radio tower, they find themselves stranded with no way down. Now Becky and Hunter’s expert climbing skills will be put to the ultimate test as they desperately fight to survive the elements, a lack of supplies, and vertigo-inducing heights",
            release_date = "2022-08-11",
        ),
    )

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
        setupAdapter()
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter(this, this)
        moviesAdapter.setList(moviesData)
        binding?.rvMovies?.adapter = moviesAdapter
        binding?.rvMovies?.setHasFixedSize(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun showMore() {

    }

    override fun onClick(item: Movie) {
        DetailActivity.startActivityModel(context, item)
    }

    override fun onClickFavorite(item: Movie) {

    }
}