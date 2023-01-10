package com.example.mytv.tv

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mytv.R
import com.example.mytv.movie.Movie
import com.google.gson.Gson

const val TV_BACKDROP = "extra_tv_backdrop"
const val TV_POSTER = "extra_tv_poster"
const val TV_TITLE = "extra_tv_title"
const val TV_RATING = "extra_tv_rating"
const val TV_RELEASE_DATE = "extra_tv_release_date"
const val TV_OVERVIEW = "extra_tv_overview"

class TvDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonVu: Button

    private lateinit var backdropvar: String
    private lateinit var postervar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)
        backdrop = findViewById(R.id.tv_backdrop)
        poster = findViewById(R.id.tv_poster)
        title = findViewById(R.id.tv_title)
        rating = findViewById(R.id.tv_rating)
        releaseDate = findViewById(R.id.tv_release_date)
        overview = findViewById(R.id.tv_overview)
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
        extras.getString(TV_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)

            backdropvar = backdropPath

        }
        extras.getString(TV_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)

            postervar = posterPath
        }
        title.text = extras.getString(TV_TITLE, "")
        rating.rating = extras.getFloat(TV_RATING, 0f) / 2
        releaseDate.text = extras.getString(TV_RELEASE_DATE, "")
        overview.text = extras.getString(TV_OVERVIEW, "")


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

    fun reallyLike(): Boolean{

        sharedPreferences = getSharedPreferences("MyFavTv",0)

        val tvs : Int = sharedPreferences.getString("value",null)!!.toInt()
        Log.d("Onclick", tvs.toString())

        for (i in 0..tvs-1){
            var data : String = sharedPreferences.getString(i.toString(),null)!!

            var tempTv : Tv = Gson().fromJson(data, Tv::class.java)
            if (tempTv.title == title.text.toString()){
                return true
            }
        }
        return false
    }

    @SuppressLint("CommitPrefEdits")
    private fun deleteData() {
        sharedPreferences = getSharedPreferences("MyFavTv",0)

        val tvs : Int = sharedPreferences.getString("value",null)!!.toInt()-1

        sharedPreferences.edit().putString("value",tvs.toString()).apply()

        for (i in 0..tvs){
            var data : String = sharedPreferences.getString(i.toString(),null)!!
            Log.d("Onclick", Gson().fromJson(data, Tv::class.java).title)


            var tempTv : Tv = Gson().fromJson(data, Tv::class.java)
            if (tempTv.title == title.text.toString()){
                Log.d("Onclick","AYAAAAA")
                sharedPreferences.edit().remove(i.toString()).commit()
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveData() {
        val tvv : Tv = Tv(-1, title.text.toString(), overview.text.toString(), postervar, backdropvar, rating.rating, releaseDate.text.toString())
        Log.d("Onclickbouttontest", Gson().toJson(tvv))

        sharedPreferences = getSharedPreferences("MyFavTv",0)
        val tvs : Int = sharedPreferences.getString("value",null)!!.toInt()+1

        sharedPreferences.edit().putString("value",tvs.toString()).apply()

        sharedPreferences.edit().putString((tvs-1).toString(), Gson().toJson(tvv)).apply()
        Log.d("Onclickboutton", sharedPreferences.all.toString(),null)

    }

    fun partage(p0: View?){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Salut ! cette série est super => " + title.text.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}