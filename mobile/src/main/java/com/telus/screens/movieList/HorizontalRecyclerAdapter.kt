package com.telus.screens.movieList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import com.telus.R
import com.telus.screens.movieList.model.Movie
import com.telus.utility.ImageResource
import io.reactivex.subjects.Subject


class HorizontalRecyclerAdapter(
        private val movies: List<Movie>,
        private val onMovieClickSubject: Subject<Pair<View, Movie?>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val cellViewHolder = viewHolder as CellViewHolder
        cellViewHolder.bindView(movies[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v1 = LayoutInflater.from(viewGroup.context).inflate(R.layout.detail_list_item_movie, viewGroup, false)
        return CellViewHolder(v1)
    }

    inner class CellViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val imageResource: ImageResource = ImageResource()

        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer) as LinearLayout
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle) as TextView
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster) as ImageView

        fun bindView(movie: Movie?) {
            with(itemView) {
                tvTitle.text = movie?.title
                val imageUri = imageResource.getDrawable(movie?.poster ?: "")
                if (imageUri != null) {
                    ivPoster.setImageResource(imageUri)
                }
            }

            llContainer.clicks()
                    .subscribe {
                        onMovieClickSubject.onNext(Pair(llContainer, movie))
                    }
        }
    }

}