package com.example.mytv.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mytv.R
import com.google.gson.Gson
import java.lang.reflect.Modifier.toString

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"


class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var buttonVu: Button
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var backdropvar: String
    private lateinit var postervar: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        buttonVu = findViewById(R.id.button_vu)
        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()

        }

        buttonVu.setOnClickListener(this);

        buttonVu.isSelected = reallyLike()
    }


    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)

            backdropvar = backdropPath
        }
        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)

            postervar = posterPath
        }
        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")


    }

    override fun onClick(p0: View?) {
        Log.d("Onclickboutton", "check")
        if(buttonVu.isSelected){
            buttonVu.isSelected = false
            deleteData()
        }else{
            buttonVu.isSelected = true
            saveData()
        }
    }

     fun partage(p0: View?){
         val sendIntent: Intent = Intent().apply {
             action = Intent.ACTION_SEND
             putExtra(Intent.EXTRA_TEXT, "Salut ! ce film est super => " + title.text.toString())
             type = "text/plain"
         }

         val shareIntent = Intent.createChooser(sendIntent, null)
         startActivity(shareIntent)
    }

    fun reallyLike(): Boolean{

        sharedPreferences = getSharedPreferences("MyFavMovie",0)

        val movies : Int = sharedPreferences.getString("value",null)!!.toInt()

        for (i in 0..movies-1){
            var data : String = sharedPreferences.getString(i.toString(),null)!!

            var tempMovie : Movie = Gson().fromJson(data, Movie::class.java)
            if (tempMovie.title == title.text.toString()){
                return true
            }
        }
        return false
    }

    @SuppressLint("CommitPrefEdits")
    private fun deleteData() {
        sharedPreferences = getSharedPreferences("MyFavMovie",0)

        val movies : Int = sharedPreferences.getString("value",null)!!.toInt()-1

        sharedPreferences.edit().putString("value",movies.toString()).apply()

        for (i in 0..movies){
            var data : String = sharedPreferences.getString(i.toString(),null)!!
            Log.d("Onclick",Gson().fromJson(data, Movie::class.java).title)


              var tempMovie : Movie = Gson().fromJson(data, Movie::class.java)
              if (tempMovie.title == title.text.toString()){
                  Log.d("Onclick","AYAAAAA")
                  sharedPreferences.edit().remove(i.toString()).commit()
              }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveData() {
        val moviee : Movie = Movie(-1, title.text.toString(), overview.text.toString(), postervar, backdropvar, rating.rating, releaseDate.text.toString())
        Log.d("Onclickbouttontest", Gson().toJson(moviee))

        sharedPreferences = getSharedPreferences("MyFavMovie",0)
        val movies : Int = sharedPreferences.getString("value",null)!!.toInt()+1

        sharedPreferences.edit().putString("value",movies.toString()).apply()

        sharedPreferences.edit().putString((movies-1).toString(),Gson().toJson(moviee)).apply()
        Log.d("Onclickboutton", sharedPreferences.all.toString(),null)

    }
}