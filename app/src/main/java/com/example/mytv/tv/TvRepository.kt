package com.example.mytv.tv

import android.util.Log
import com.example.mytv.Api
import com.example.mytv.movie.GetMoviesResponse
import com.example.mytv.movie.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvRepository {
    private val api: Api
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getPopularTv(page: Int = 1,
                     onSuccess: (tv: List<Tv>) -> Unit,
                     onError: () -> Unit) {
        api.getPopularTv(page = page)
            .enqueue(object : Callback<GetTvResponse> {
                override fun onResponse(
                    call: Call<GetTvResponse>,
                    response: Response<GetTvResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tv)
                            Log.d("Repository", "Movies: ${responseBody.tv}")
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }
                override fun onFailure(call: Call<GetTvResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }


    fun getTopRatedTv(
        page: Int = 1,
        onSuccess: (tv: List<Tv>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedTv(page = page)
            .enqueue(object : Callback<GetTvResponse> {
                override fun onResponse(
                    call: Call<GetTvResponse>,
                    response: Response<GetTvResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tv)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }
                override fun onFailure(call: Call<GetTvResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }


}



