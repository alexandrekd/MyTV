package com.example.mytv.tv

import com.google.gson.annotations.SerializedName

class GetTvResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tv: List<Tv>,
    @SerializedName("total_results") val results: Int,
    @SerializedName("total_pages") val pages: Int
        )