package com.abdelmageed.mazadytask.presentation.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.abdelmageed.mazadytask.R
import com.abdelmageed.mazadytask.data.model.MovieDto
import com.abdelmageed.mazadytask.data.remote.response.MoviesResponseItem
import com.abdelmageed.mazadytask.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onClick: (MovieDto) -> Unit,
    val onFavoriteClick: (MovieDto) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
//    lateinit var binding: ItemMovieBinding

    private var movies: MutableList<MovieDto> = mutableListOf()

    fun setList(list: List<MovieDto>) {
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun removeAll(){
        movies.clear()
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: MovieDto, position: Int) {
            binding.tvTitle.text = movie.title ?: ""
            binding.tvReleaseDate.text = "Release Year:${movie.year ?: ""}"
            binding.ivFavorite.setImageResource(R.drawable.ic_heart)
            binding.ivPoster.load(movie.poster)
            itemView.setOnClickListener {
                onClick(movie)
            }

            binding.ivFavorite.setOnClickListener { _ ->
                onFavoriteClick(movie)
                notifyItemRemoved(position)

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], position)
    }
}