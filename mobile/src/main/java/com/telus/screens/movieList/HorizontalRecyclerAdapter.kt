package com.telus.screens.movieList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.telus.R
import com.telus.screens.movieList.model.Movie
import com.telus.utility.ImageResource


class HorizontalRecyclerAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mItemClickListener: OnItemClickListener
    private val imageResource: ImageResource = ImageResource()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v1 = LayoutInflater.from(viewGroup.context).inflate(R.layout.detail_list_item_movie, viewGroup, false)
        return CellViewHolder(v1, mItemClickListener, movies)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val cellViewHolder = viewHolder as CellViewHolder
        cellViewHolder.tvTitle.text = movies[position].title
        val imageUri = imageResource.getDrawable(movies[position].poster)
        if (imageUri != null) {
            cellViewHolder.ivPoster.setImageResource(imageUri)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    private class CellViewHolder(itemView: View, val listener: OnItemClickListener, val movies: List<Movie>) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val llContainer: LinearLayout = itemView.findViewById(R.id.llContainer) as LinearLayout
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle) as TextView
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster) as ImageView

        override fun onClick(view: View?) {
            if (view != null) {
                listener.onItemClick(movies[layoutPosition])
            }
        }

        init {
            llContainer.setOnClickListener(this)
        }
    }

}