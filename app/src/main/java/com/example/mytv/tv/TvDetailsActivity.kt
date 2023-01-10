package com.example.mytv.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mytv.R

const val TV_BACKDROP = "extra_tv_backdrop"
const val TV_POSTER = "extra_tv_poster"
const val TV_TITLE = "extra_tv_title"
const val TV_RATING = "extra_tv_rating"
const val TV_RELEASE_DATE = "extra_tv_release_date"
const val TV_OVERVIEW = "extra_tv_overview"

class TvDetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)
        backdrop = findViewById(R.id.tv_backdrop)
        poster = findViewById(R.id.tv_poster)
        title = findViewById(R.id.tv_title)
        rating = findViewById(R.id.tv_rating)
        releaseDate = findViewById(R.id.tv_release_date)
        overview = findViewById(R.id.tv_overview)

        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }
    private fun populateDetails(extras: Bundle) {
        extras.getString(TV_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }
        extras.getString(TV_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        title.text = extras.getString(TV_TITLE, "")
        rating.rating = extras.getFloat(TV_RATING, 0f) / 2
        releaseDate.text = extras.getString(TV_RELEASE_DATE, "")
        overview.text = extras.getString(TV_OVERVIEW, "")
    }
}