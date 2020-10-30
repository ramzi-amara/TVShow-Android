package com.d74.tp1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d74.tp1.R
import com.example.tp1.models.Season
import com.squareup.picasso.Picasso

class ListeSeasonRecycleViewAdapter(private val listeSeason: MutableList<Season>) :
    RecyclerView.Adapter<ListeSeasonRecycleViewAdapter.SeasonViewHolder>() {

    class SeasonViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.season_item, parent, false) as View
        return SeasonViewHolder(view)
    }

    override fun onBindViewHolder(holderSeason: SeasonViewHolder, position: Int) {
        val imgSeason = holderSeason.view.findViewById<ImageView>(R.id.img_Season)
        Picasso.get().load(this.listeSeason[position].Image).into(imgSeason)
        holderSeason.view.findViewById<TextView>(R.id.txt_SeasonNumber).text = "Season " + this.listeSeason[position].Number.toString()
        holderSeason.view.findViewById<TextView>(R.id.txt_EpisodesCount).text = this.listeSeason[position].EpisodeCount.toString() + " Episodes"
        println("SeasonNumber:" + this.listeSeason[position].Number.toString())

    }
    override fun getItemCount() = this.listeSeason.size
}