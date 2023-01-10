package com.example.mytv.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytv.R
import com.example.mytv.movie.*
import com.example.mytv.profil.ProfilPage
import com.example.mytv.tv.TvPage
import com.google.android.material.navigation.NavigationBarView
import kotlin.math.log

class SearchPage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var search: RecyclerView
    private lateinit var searchAdapter: SearchAdapter

    lateinit var searchView: SearchView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        SearchRepository.getSearch(
            search = "Iron",
            onSuccess = ::onPopularSearchFetched,
            onError = ::onError)

        search = findViewById(R.id.search)
        search.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        searchAdapter = SearchAdapter(listOf()) {search -> showMovieDetails(search) }
        search.adapter = searchAdapter

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


        // initializing variables of list view with their ids.
        searchView = findViewById(R.id.search_bar)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    Log.d("Welcome to Disneyland Paris",p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    SearchRepository.getSearch(
                        search = p0,
                        onSuccess = ::onPopularSearchFetched,
                        onError = ::onError)
                }
                return true
            }

        })


    }
    private fun onPopularSearchFetched(search: List<Search>) {
        searchAdapter.updateSearch(search)
    }
    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(search: Search) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, search.backdropPath)
        intent.putExtra(MOVIE_POSTER, search.posterPath)
        intent.putExtra(MOVIE_TITLE, search.title)
        intent.putExtra(MOVIE_RATING, search.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, search.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, search.overview)
        startActivity(intent)
    }
}