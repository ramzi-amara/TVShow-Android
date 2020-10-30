package com.d74.tp1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.d74.tp1.R
import com.example.tp1.models.TvShow
import com.squareup.picasso.Picasso

class ListeTvShowRecyclerViewAdapter(private val datasetTvShow: Array<TvShow>) :
    RecyclerView.Adapter<ListeTvShowRecyclerViewAdapter.TvShowViewHolder>() {

    class TvShowViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tvshow_item, parent, false) as View
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holderTvShow: TvShowViewHolder, position: Int) {
        val imgTvShow=holderTvShow.view.findViewById<ImageView>(R.id.imgTvShow)
        Picasso.get().load(this.datasetTvShow[position].Image).into(imgTvShow)
        holderTvShow.view.setOnClickListener{
            val param = bundleOf(Pair("TvShowId", this.datasetTvShow[position].TvShowId))
            holderTvShow.view.findNavController().navigate(R.id.detailsTvShowFragment, param)
        }
    }
    override fun getItemCount() = this.datasetTvShow.size
}