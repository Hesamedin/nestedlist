package com.telus.screens.movieList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.telus.R
import com.telus.screens.movieDetail.MovieDetailActivity
import com.telus.screens.movieList.model.Movie
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.SortedMap

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }

    private val changeObserver = Observer<SortedMap<String, List<Movie>>> { value ->
        value?.let { displayData(value) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.changeNotifier.observe(this, changeObserver)
        viewModel.displayMoviesFromAsset(assets.open("assets.json"))

        lifecycle.addObserver(viewModel)

        //
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(false)
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
    }

    private fun displayData(sortedList: SortedMap<String, List<Movie>>) {
        sortedList.forEach { t, u ->  Timber.d(">> $t - ${u.forEach { it.title }}")}

        val onClickMovieSubject = PublishSubject.create<Pair<View, Movie?>>()
        onClickMovieSubject
                .subscribeBy {
                    val movie = it.second
                    if (movie == null) {
                        Timber.e("Movie should not be null!")
                        return@subscribeBy
                    }

                    Timber.d(movie.toString())
                    MovieDetailActivity.start(this, movie)
                }
        val adapter = VerticalRecyclerAdapter(sortedList, onClickMovieSubject)
        mRecyclerView.adapter = adapter
    }
}
