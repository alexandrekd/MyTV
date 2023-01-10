package com.example.mytv.tv

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytv.R
import com.example.mytv.movie.MoviePage
import com.example.mytv.profil.ProfilPage
import com.example.mytv.search.SearchPage
import com.google.android.material.navigation.NavigationBarView

class TvPage : AppCompatActivity() {

    private lateinit var popularTv: RecyclerView
    private lateinit var popularTvAdapter: TvAdapter
    private lateinit var popularTvLayoutMgr: LinearLayoutManager
    private var popularTvPage = 1

    private lateinit var topRatedTv: RecyclerView
    private lateinit var topRatedTvAdapter: TvAdapter
    private lateinit var topRatedTvLayoutMgr: LinearLayoutManager
    private var topRatedTvPage = 1



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv)

        popularTv = findViewById(R.id.popular_tv)
        popularTvLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularTv.layoutManager = popularTvLayoutMgr
        popularTvAdapter = TvAdapter(mutableListOf()) { tv -> showTvDetails(tv)}
        popularTv.adapter = popularTvAdapter


        TvRepository.getPopularTv(
            popularTvPage,
            onSuccess = ::onPopularTvFetched,
            onError = ::onError
        )

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

        topRatedTv = findViewById(R.id.top_rated_tv)
        topRatedTvLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedTv.layoutManager = topRatedTvLayoutMgr
        topRatedTvAdapter = TvAdapter(mutableListOf()) { tv -> showTvDetails(tv)}
        topRatedTv.adapter = topRatedTvAdapter


        getPopularTv()
        getTopRatedTv()

    }

    private fun onPopularTvFetched(tv: List<Tv>) {
        popularTvAdapter.appendTv(tv)
        attachPopularTvOnScrollListener()
    }
    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun getPopularTv() {
        TvRepository.getPopularTv(
            popularTvPage,
            ::onPopularTvFetched,
            ::onError
        )
    }

    private fun attachPopularTvOnScrollListener() {
        popularTv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularTvLayoutMgr.itemCount
                18
                val visibleItemCount = popularTvLayoutMgr.childCount
                val firstVisibleItem = popularTvLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularTv.removeOnScrollListener(this)
                    popularTvPage++
                    getPopularTv()
                }
            }
        })
    }

    private fun getTopRatedTv() {
        TvRepository.getTopRatedTv(
            topRatedTvPage,
            ::onTopRatedTvFetched,
            ::onError
        )
    }
    private fun attachTopRatedTvOnScrollListener() {
        topRatedTv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedTvLayoutMgr.itemCount
                val visibleItemCount = topRatedTvLayoutMgr.childCount
                val firstVisibleItem = topRatedTvLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedTv.removeOnScrollListener(this)
                    topRatedTvPage++
                    getTopRatedTv()
                }
            }
        })
    }
    private fun onTopRatedTvFetched(tv: List<Tv>) {
        topRatedTvAdapter.appendTv(tv)
        attachTopRatedTvOnScrollListener()
    }

    private fun showTvDetails(tv: Tv) {
        val intent = Intent(this, TvDetailsActivity::class.java)
        intent.putExtra(TV_BACKDROP, tv.backdropPath)
        intent.putExtra(TV_POSTER, tv.posterPath)
        intent.putExtra(TV_TITLE, tv.title)
        intent.putExtra(TV_RATING, tv.rating)
        intent.putExtra(TV_RELEASE_DATE, tv.releaseDate)
        intent.putExtra(TV_OVERVIEW, tv.overview)
        startActivity(intent)
    }

}