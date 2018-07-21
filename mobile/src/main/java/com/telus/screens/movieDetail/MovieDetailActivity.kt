package com.telus.screens.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.telus.R
import com.telus.screens.movieList.model.Movie
import com.telus.screens.videoPlayer.VideoPlayerActivity
import com.telus.utility.ImageResource
import timber.log.Timber

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private const val KEY: String = "MOVIE_DETAIL"

        fun start(context: Context, movie: Movie) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(KEY, movie)
            context.startActivity(intent)
        }
    }

    lateinit var ivPoster : ImageView
    lateinit var tvTitle1 : TextView
    lateinit var tvTitle2 : TextView
    lateinit var tvYear : TextView
    lateinit var tvRating : TextView
    lateinit var tvLength : TextView
    lateinit var tvDescription : TextView
    lateinit var btnPlay : Button
    lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        if (!intent.extras.containsKey(KEY)) {
            Toast.makeText(this, "No Data has passed!", Toast.LENGTH_LONG).show()
            return
        }

        setupViews()

        val movie: Movie = intent.extras[KEY] as Movie
        Timber.d(">> Movie: ${movie.title}")
        tvTitle1.text = movie.title
        tvTitle2.text = "Title: ${movie.title}"
        tvYear.text = "Year: ${movie.year}"
        tvRating.text = "Rating: ${movie.rating}"
        tvLength.text ="Length: ${movie.length}"
        tvDescription.text = movie.description
        ivPoster.setImageResource(ImageResource().getDrawable(movie.poster) ?: return)

        btnBack.setOnClickListener { _ -> finish() }
        btnPlay.setOnClickListener { _ -> VideoPlayerActivity.start(this) }
    }

    private fun setupViews() {
        ivPoster = findViewById(R.id.ivPoster)
        tvTitle1 = findViewById(R.id.tvTitle1)
        tvTitle2 = findViewById(R.id.tvTitle2)
        tvYear = findViewById(R.id.tvYear)
        tvRating = findViewById(R.id.tvRating)
        tvLength = findViewById(R.id.tvLength)
        tvDescription = findViewById(R.id.tvDescription)
        btnPlay = findViewById(R.id.btnPlay)
        btnBack = findViewById(R.id.btnBack)
    }
}