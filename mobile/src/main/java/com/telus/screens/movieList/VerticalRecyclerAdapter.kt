package com.telus.screens.movieList

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.SparseIntArray
import android.widget.TextView
import com.telus.R
import com.telus.screens.movieList.model.Movie
import io.reactivex.subjects.Subject
import java.util.SortedMap

class VerticalRecyclerAdapter(
        private val sortedList: SortedMap<String, List<Movie>>,
        val onMovieClickSubject: Subject<Pair<View, Movie?>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mContext: Context
    private val listPosition: SparseIntArray = SparseIntArray()

    override fun getItemCount(): Int {
        return sortedList.keys.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val keys = ArrayList<String>(5)
        sortedList.forEach { t, _ -> keys.add(t) }
        val key: String = keys[position]

        val cellViewHolder = viewHolder as CellViewHolder
        cellViewHolder.bindView(key, sortedList.getValue(key), position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = viewGroup.context

        val v1 = LayoutInflater.from(mContext).inflate(R.layout.detail_list_item_vertical, viewGroup, false)
        return CellViewHolder(v1)
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val cellViewHolder = viewHolder as CellViewHolder
        val layoutManager = (cellViewHolder.mRecyclerView.layoutManager as LinearLayoutManager)
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        listPosition.put(position, firstVisiblePosition)

        super.onViewRecycled(viewHolder)
    }

    inner class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView) as RecyclerView
        private val mCategory: TextView = itemView.findViewById(R.id.tvCategory) as TextView

        fun bindView(category: String, movies: List<Movie>, position: Int) {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL

            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = layoutManager

            with(itemView) {
                mCategory.text = category
                val adapter = HorizontalRecyclerAdapter(movies, this@VerticalRecyclerAdapter.onMovieClickSubject)
                mRecyclerView.adapter = adapter

                val lastSeenFirstPosition = listPosition.get(position, 0)
                if (lastSeenFirstPosition >= 0) {
                    mRecyclerView.scrollToPosition(lastSeenFirstPosition)
                }
            }
        }
    }
}