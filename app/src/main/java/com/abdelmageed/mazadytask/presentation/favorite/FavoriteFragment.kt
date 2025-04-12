package com.abdelmageed.mazadytask.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.mazadytask.databinding.FragmentFavoriteBinding
import com.abdelmageed.mazadytask.extension.toFavoriteEntity
import com.abdelmageed.mazadytask.extension.toMovieDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    lateinit var adapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavorites()
        adapter = MoviesAdapter({
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailsFragment(
                    it
                )
            )
        }, {
            viewModel.toggleFavorite(it.toFavoriteEntity())
            adapter.removeAll()
            viewModel.getFavorites()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.apply {

            binding.rvMovies.adapter = adapter
            binding.rvMovies.layoutManager = LinearLayoutManager(requireActivity())

            binding.ivGrid.setOnClickListener {
                binding.rvMovies.adapter = adapter
                binding.rvMovies.layoutManager = GridLayoutManager(requireActivity(), 2)
            }

            binding.ivList.setOnClickListener {
                binding.rvMovies.adapter = adapter
                binding.rvMovies.layoutManager = LinearLayoutManager(requireActivity())
            }

        }
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.favoritesFlow.collectLatest { favoriteList ->
                adapter.setList(favoriteList.map { it.toMovieDto() })
            }
        }
    }

}