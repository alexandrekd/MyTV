package com.example.mytv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytv.movie.MoviePage

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val object_a = Intent(ActivityA@this, MoviePage::class.java)
        startActivity(object_a)




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