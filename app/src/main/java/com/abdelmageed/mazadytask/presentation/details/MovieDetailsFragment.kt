package com.abdelmageed.mazadytask.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.Coil
import coil.api.load
import com.abdelmageed.mazadytask.databinding.FragmentMovieDetailsBinding
import com.abdelmageed.mazadytask.extension.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val movie = args.movieItem
            Log.e("movieData", "$movie")
            ivPoster.loadImage(movie.poster ?: "")
            tvTitle.text = movie.title
            tvGenres.text = movie.genres
            tvOverView.text = movie.overview
            tvRating.text = "${movie.rating}"

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}