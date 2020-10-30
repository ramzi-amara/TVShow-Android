package com.d74.tp1.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.d74.tp1.R
import com.d74.tp1.adapters.ListeTvShowRecyclerViewAdapter
import com.example.tp1.models.TvShow
import com.google.gson.Gson

class TvShowsFragment : Fragment() {

    private  var datasetTvShow: Array<TvShow> = arrayOf()
    private lateinit var rvListeTvShow: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedLayout= inflater.inflate(R.layout.fragment_tv_shows, container, false)
        this.rvListeTvShow=inflatedLayout.findViewById(R.id.rvTvShow)
        this.rvListeTvShow.layoutManager= GridLayoutManager(this.context,2)
        this.rvListeTvShow.adapter = ListeTvShowRecyclerViewAdapter(this.datasetTvShow)

        getListeTvShow()
        return inflatedLayout

    }
    private fun getListeTvShow() {
        val queue = Volley.newRequestQueue(this.context)
        val url = "http://tvshowapi.sv55.cmaisonneuve.qc.ca/api/tvshows"
        val jsonRequest = StringRequest (
            Request.Method.GET,
            url,
            Response.Listener {
                val gson = Gson()
                this.datasetTvShow=gson.fromJson(it, Array<TvShow>::class.java)
                this.rvListeTvShow.adapter=
                    ListeTvShowRecyclerViewAdapter(
                        datasetTvShow
                    )
                println(this.datasetTvShow)
            },
            Response.ErrorListener {
                //Traiter la réponse lorsqu'une erreur se produit (it contient erreur)
                println("ERREUR: /api/artistes")
            })
        queue.add(jsonRequest)
    }
}
