package com.example.mytv.search

import com.google.gson.annotations.SerializedName

data class GetSearchResponse(@SerializedName("page") val page: Int,
                             @SerializedName("results") val searchList: List<Search>,
                             @SerializedName("total_pages") val pages: Int) {
}
