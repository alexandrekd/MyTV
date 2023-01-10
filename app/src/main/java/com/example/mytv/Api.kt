package com.example.mytv

import com.example.mytv.movie.GetMoviesResponse
import com.example.mytv.tv.GetTvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "9ebb909aaf76048c85007618c28b505f",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "9ebb909aaf76048c85007618c28b505f",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "9ebb909aaf76048c85007618c28b505f",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("tv/popular")
    fun getPopularTv(
        @Query("api_key") apiKey: String = "9ebb909aaf76048c85007618c28b505f",
        @Query("page") page: Int
    ): Call<GetTvResponse>

    @GET("tv/top_rated")
    fun getTopRatedTv(
        @Query("api_key") apiKey: String = "9ebb909aaf76048c85007618c28b505f",
        @Query("page") page: Int
    ): Call<GetTvResponse>


}