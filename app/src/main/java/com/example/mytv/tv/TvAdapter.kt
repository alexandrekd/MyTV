package com.example.mytv.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mytv.R

class TvAdapter(
    private var tv: MutableList<Tv>,
    private val onTvClick: (tv: Tv) -> Unit
) : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return TvViewHolder(view)
    }
    override fun getItemCount(): Int = tv.size
    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(tv[position])
    }

    inner class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        fun bind(tv: Tv) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${tv.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener { onTvClick.invoke(tv) }
        }
    }

    fun appendTv(tv: List<Tv>) {
        this.tv.addAll(tv)
        notifyItemRangeInserted(
            this.tv.size,
            tv.size - 1
        )
    }
}