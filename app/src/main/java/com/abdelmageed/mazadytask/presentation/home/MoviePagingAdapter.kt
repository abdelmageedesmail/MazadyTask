package com.abdelmageed.mazadytask.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.api.load
import com.abdelmageed.mazadytask.R
import com.abdelmageed.mazadytask.data.remote.response.ResultsItem
import com.abdelmageed.mazadytask.databinding.ItemGridMovieBinding
import com.abdelmageed.mazadytask.databinding.ItemMovieBinding
import com.abdelmageed.mazadytask.extension.loadImage

class MoviePagingAdapter(
    val onClick: (ResultsItem?) -> Unit,
    val onFavoriteClick: (ResultsItem) -> Unit
) : PagingDataAdapter<ResultsItem, MoviePagingAdapter.MovieViewHolder>(DiffCallback) {

    private var itemType: Int = 0

    object DiffCallback : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(
            oldItem: ResultsItem,
            newItem: ResultsItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultsItem,
            newItem: ResultsItem
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.poster_path == newItem.poster_path &&
                    oldItem.release_date == newItem.release_date &&
                    oldItem.isFavoriteItem == newItem.isFavoriteItem

        }
    }

    inner class MovieViewHolder(private val binding: ItemGridMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ResultsItem?) {
            item?.let {
                Log.d("imageGenres", "${it.genre_ids}")
                binding.tvTitle.text = it.title ?: ""
                binding.tvReleaseDate.text = "Release Year:${it.release_date ?: ""}"
                if (it.isFavoriteItem) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_heart)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_un_favorite)
                }
                Log.d("imagePoster", "${it.poster_path}")
                binding.ivPoster.loadImage("${it.poster_path}")
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
        val binding =
            ItemGridMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

}