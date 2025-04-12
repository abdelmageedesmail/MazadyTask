package com.abdelmageed.mazadytask.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.mazadytask.R
import com.abdelmageed.mazadytask.databinding.FragmentHomeBinding
import com.abdelmageed.mazadytask.extension.toMovieDto
import com.abdelmageed.mazadytask.extension.visibility
import com.abdelmageed.mazadytask.util.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MoviePagingAdapter
    private var viewType: Int = 0
    private lateinit var networkMonitor: NetworkMonitor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MoviePagingAdapter({
            it?.let { item ->
                val movieItem = item.toMovieDto()
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieDetailsFragment(movieItem)
                findNavController().navigate(action)
            }
        }, { movie ->
            movie.let { it1 -> viewModel.toggleFavorite(it1) }
            adapter.refresh()
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        networkMonitor = NetworkMonitor(requireActivity())

        networkMonitor.isConnected.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                viewModel.getMovies()
            }
        }

        networkMonitor.startMonitoring()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.apply {
            binding.rvMovies.adapter = adapter
            binding.rvMovies.layoutManager = LinearLayoutManager(requireActivity())
            binding.ivGrid.setOnClickListener {
                viewModel.isGridLayout = true
                setupRecyclerView()
            }

            binding.ivList.setOnClickListener {
                viewModel.isGridLayout = false
                setupRecyclerView()
            }
            binding.ivHeart.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvMovies.layoutManager = if (viewModel.isGridLayout) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
        binding.rvMovies.adapter = adapter

        binding.rvMovies.post {
            viewModel.recyclerViewState?.let {
                binding.rvMovies.layoutManager?.onRestoreInstanceState(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.recyclerViewState = binding.rvMovies.layoutManager?.onSaveInstanceState()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.moviesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)

            }
        }
    }
}