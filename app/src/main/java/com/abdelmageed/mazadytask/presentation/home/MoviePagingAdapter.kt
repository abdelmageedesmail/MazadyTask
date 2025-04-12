package com.abdelmageed.mazadytask.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.abdelmageed.mazadytask.R
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.databinding.ItemMovieBinding

class MoviePagingAdapter(
    val onClick: (MoviesResponseItem?) -> Unit,
    val onFavoriteClick: (MoviesResponseItem) -> Unit
) : PagingDataAdapter<MoviesResponseItem, MoviePagingAdapter.MovieViewHolder>(DiffCallback) {


    object DiffCallback : DiffUtil.ItemCallback<MoviesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: MoviesResponseItem,
            newItem: MoviesResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesResponseItem,
            newItem: MoviesResponseItem
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.poster == newItem.poster &&
                    oldItem.year == newItem.year &&
                    oldItem.isFavoriteItem == newItem.isFavoriteItem

        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MoviesResponseItem?) {
            item?.let {
                binding.tvTitle.text = it.title ?: ""
                binding.tvReleaseDate.text = "Release Year:${it.year ?: ""}"
                if (it.isFavoriteItem) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_heart)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_un_favorite)
                }
                binding.ivPoster.load(it.poster)
                binding.root.setOnClickListener { _ ->
                    onClick(it)
                }
                binding.ivFavorite.setOnClickListener { _ ->
                    onFavoriteClick(it)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }
}