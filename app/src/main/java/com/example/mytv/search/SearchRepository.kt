package com.example.mytv.search

import android.util.Log
import com.example.mytv.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRepository {
    private val api: Api
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getSearch(page: Int = 1,
                  search: String,
                  onSuccess: (search: List<Search>) -> Unit,
                  onError: () -> Unit) {
        api.getSearch(page = page, query = search)
            .enqueue(object : Callback<GetSearchResponse> {
                override fun onResponse(
                    call: Call<GetSearchResponse>,
                    response: Response<GetSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.searchList)
                        } else {
                            onError.invoke()                        }
                    }
                }
                override fun onFailure(call: Call<GetSearchResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}