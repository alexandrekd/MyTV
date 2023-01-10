package com.example.mytv.profil

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytv.R
import com.example.mytv.movie.Movie
import com.example.mytv.movie.MoviePage
import com.example.mytv.movie.MoviesAdapter
import com.example.mytv.search.SearchPage
import com.example.mytv.tv.Tv
import com.example.mytv.tv.TvPage
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson

private lateinit var seeMovie: RecyclerView
private lateinit var profilAdapter: ProfilAdapter
private lateinit var sharedPreferences: SharedPreferences
private lateinit var objectList : MutableList<Movie>
private lateinit var seeTv: RecyclerView
private lateinit var objectListTv : MutableList<Movie>
private lateinit var objectListMovie : MutableList<Movie>




class ProfilPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val navBar = findViewById<NavigationBarView>(R.id.bottonnav)

        navBar.setOnItemSelectedListener {

            when (it.itemId){
                R.id.profil -> {
                    val object_a = Intent(ActivityA@this, ProfilPage::class.java)
                    startActivity(object_a)
                    true}
                R.id.movie -> {
                    val object_a = Intent(ActivityA@this, MoviePage::class.java)
                    startActivity(object_a)
                    true}
                R.id.tv -> {val object_a = Intent(ActivityA@this, TvPage::class.java)
                    startActivity(object_a)
                    true}
                R.id.search -> {
                    val object_a = Intent(ActivityA@this, SearchPage::class.java)
                    startActivity(object_a)
                    true}
                else -> {
                    Log.d("Bottom bar : ", "Else")
                    false}

            }
        }

        seeTv = findViewById(R.id.see_tv)
        seeTv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        profilAdapter = ProfilAdapter(listOf())
        seeTv.adapter = profilAdapter

        sharedPreferences = getSharedPreferences("MyFavTv",0)

        objectListTv = mutableListOf(Movie(id=-1, title="M3GAN", overview="A brilliant toy company roboticist uses artificial intelligence to develop M3GAN, a life-like doll programmed to emotionally bond with her newly orphaned niece. But when the doll's programming works too well, she becomes overprotective of her new friend with terrifying results.", posterPath="/7CNCv9uhqdwK7Fv4bR4nmDysnd9.jpg", backdropPath="/5kAGbi9MFAobQTVfK4kWPnIfnP0.jpg", rating= 3.5F, releaseDate="2022-12-28"))

        val movies : Int = sharedPreferences.getString("value",null)!!.toInt()

        for (i in 0..movies-1){
            var data : String = sharedPreferences.getString(i.toString(),null)!!

            var tempTv : Movie = Gson().fromJson(data, Movie::class.java)
            objectListTv.add(tempTv)
        }


        onPopularMoviesFetched(objectListTv)



        seeMovie = findViewById(R.id.see_movie)
        seeMovie.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        profilAdapter = ProfilAdapter(listOf())
        seeMovie.adapter = profilAdapter

        sharedPreferences = getSharedPreferences("MyFavMovie",0)

        objectListMovie = mutableListOf(Movie(id=-1, title="M3GAN", overview="A brilliant toy company roboticist uses artificial intelligence to develop M3GAN, a life-like doll programmed to emotionally bond with her newly orphaned niece. But when the doll's programming works too well, she becomes overprotective of her new friend with terrifying results.", posterPath="/7CNCv9uhqdwK7Fv4bR4nmDysnd9.jpg", backdropPath="/5kAGbi9MFAobQTVfK4kWPnIfnP0.jpg", rating= 3.5F, releaseDate="2022-12-28"))

        val tvs : Int = sharedPreferences.getString("value",null)!!.toInt()

        for (i in 0..tvs-1){
            var data : String = sharedPreferences.getString(i.toString(),null)!!

            var tempMovie : Movie = Gson().fromJson(data, Movie::class.java)
            objectListMovie.add(tempMovie)
        }



        onPopularMoviesFetched(objectListMovie)
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        profilAdapter.updateMovies(movies)
    }

}