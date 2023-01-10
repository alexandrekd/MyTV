package com.example.mytv

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mytv.movie.MoviePage

class MainActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences



    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val object_a = Intent(ActivityA@this, MoviePage::class.java)
        startActivity(object_a)


        sharedPreferences = getSharedPreferences("MyFavMovie",0)
        sharedPreferences.edit().putString("value", "0").apply()

        sharedPreferences = getSharedPreferences("MyFavTv",0)
        sharedPreferences.edit().putString("value", "0").apply()





    }







    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("Bottom bar : ", "click")

        when (item.itemId){
            R.id.profil -> Log.d("Bottom bar : ", "Profil")
            R.id.movie -> Log.d("Bottom bar : ", "Movie")
            R.id.tv -> Log.d("Bottom bar : ", "Tv")
            R.id.search -> Log.d("Bottom bar : ", "Search")
            else -> Log.d("Bottom bar : ", "Else")

        }
        return true;
    }*/
}