package com.example.mytv.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mytv.R

class SearchAdapter(
    private var search: List<Search>,
    private val onSearchClick: (search: Search) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return SearchViewHolder(view)
    }
    override fun getItemCount(): Int = search.size
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(search[position])
    }
    fun updateSearch(search: List<Search>) {
        this.search = search
        notifyDataSetChanged()
    }
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        fun bind(search: Search) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${search.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener { onSearchClick.invoke(search) }
        }
    }
}