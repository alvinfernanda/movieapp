package com.alvinfernanda.mobile.movieapp.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alvinfernanda.mobile.movieapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context))
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}