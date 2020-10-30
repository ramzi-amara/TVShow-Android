package com.d74.tp1.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.d74.tp1.R
import com.d74.tp1.adapters.ListeRoleRecycleViewAdapter
import com.d74.tp1.adapters.ListeSeasonRecycleViewAdapter
import com.example.tp1.models.DetailsTvShow
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import layout.AuthentificatedRequest


class DetailsTvShowFragment : Fragment() {
    private lateinit var detailsTvShow_data : DetailsTvShow
    private lateinit var rv_role : RecyclerView
    private lateinit var rv_seasons : RecyclerView
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var inflatedLayout = inflater.inflate(R.layout.fragment_details_tv_show, container, false)

        this.rv_role = inflatedLayout.findViewById<RecyclerView>(R.id.rv_Role)
        this.rv_seasons = inflatedLayout.findViewById<RecyclerView>(R.id.rv_Seasons)

        this.rv_role.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        this.rv_seasons.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        val tvShowId : Int = this.requireArguments().getInt("TvShowId", 0)
        GetDetailsTvShow(tvShowId)


        return inflatedLayout
    }

    private fun GetDetailsTvShow(TvShowId : Int) {
        val url = "http://tvshowapi.sv55.cmaisonneuve.qc.ca/api/tvshow?TvShowId=${TvShowId}"
        val queue = Volley.newRequestQueue(this.context)
        //val titre : TextView = this.requireView().findViewById<TextView>(R.id.txt_Title)
        //val img : ImageView = this.requireView().findViewById<ImageView>(R.id.imgTvShow)
        //val year : TextView = this.requireView().findViewById(R.id.txt_Year)
        //val nbrEpisodes : TextView = this.requireView().findViewById(R.id.txt_NombreEpisodes)
        //val resume : TextView = this.requireView().findViewById(R.id.txt_resume)

        val jsonRequest = StringRequest (
            Request.Method.GET,
            url,
            Response.Listener {
                val gson = Gson()
                this.detailsTvShow_data = gson.fromJson(it, DetailsTvShow::class.java)
                println(this.detailsTvShow_data)


                val img : ImageView = this.requireView().findViewById<ImageView>(R.id.imgDetailsTvShow)
                Picasso.get().load(this.detailsTvShow_data.Image).into(img)
                this.requireView().findViewById<TextView>(R.id.txt_Title).text = this.detailsTvShow_data.Title
                this.requireView().findViewById<TextView>(R.id.txt_Year).text = this.detailsTvShow_data.Year.toString()
                this.requireView().findViewById<TextView>(R.id.txt_NombreEpisodes).text = this.detailsTvShow_data.EpisodeCount.toString() + " episodes"
                this.requireView().findViewById<TextView>(R.id.txt_resume).text = this.detailsTvShow_data.Plot

                this.rv_role.adapter = ListeRoleRecycleViewAdapter(detailsTvShow_data.Roles)
                this.rv_seasons.adapter = ListeSeasonRecycleViewAdapter(detailsTvShow_data.Seasons)

                GestionsFavorites(detailsTvShow_data.TvShowId)
            },
            Response.ErrorListener {
                //Traiter la réponse lorsqu'une erreur se produit (it contient erreur)
                println("ERREUR")
            })
        queue.add(jsonRequest)
    }

    private fun GestionsFavorites(TvShowId : Int){
        val queue = Volley.newRequestQueue(this.context)
        val url = "http://tvshowapi.sv55.cmaisonneuve.qc.ca/api/userfavorites?tvshowid=${TvShowId}"
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val bearerToken = sharedPref.getString("token","").toString()
        val gestionFavoriteBtn : ImageButton = this.requireView().findViewById<ImageButton>(R.id.imgBtn_Favorite_DetailsTvShow)

        val r = AuthentificatedRequest(
            Request.Method.GET,
            url,
            bearerToken,
            Response.Listener {
                if(bearerToken != null){

                    if(it == "true"){
                        gestionFavoriteBtn.setImageResource(R.drawable.ic_baseline_star_24)
                        gestionFavoriteBtn.setOnClickListener(){
                            DeleteTvShow(TvShowId)
                            gestionFavoriteBtn.setImageResource(R.drawable.ic_baseline_star_border_24)
                            Toast.makeText(activity, "TvShow removed to favorite ", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        gestionFavoriteBtn.setImageResource(R.drawable.ic_baseline_star_border_24)
                        gestionFavoriteBtn.setOnClickListener(){
                            AjoutTvShow(TvShowId)
                            gestionFavoriteBtn.setImageResource(R.drawable.ic_baseline_star_24)
                            Toast.makeText(activity, "TvShow added to favorite ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else
                    gestionFavoriteBtn.isInvisible
            },
            Response.ErrorListener {
                println(bearerToken)
            }
        )
        queue.add(r)
    }

    private fun AjoutTvShow(TvShowId : Int){
        val queue = Volley.newRequestQueue(this.context)
        val url = "http://tvshowapi.sv55.cmaisonneuve.qc.ca/api/userfavorites?tvshowid=${TvShowId}"
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val bearerToken = sharedPref.getString("token","").toString()

        val r = AuthentificatedRequest(
            Request.Method.POST,
            url,
            bearerToken,
            Response.Listener {

            },
            Response.ErrorListener {
                println(bearerToken)
            }
        )
        queue.add(r)
    }

    private fun DeleteTvShow(TvShowId : Int){
        val queue = Volley.newRequestQueue(this.context)
        val url = "http://tvshowapi.sv55.cmaisonneuve.qc.ca/api/userfavorites?tvshowid=${TvShowId}"
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val bearerToken = sharedPref.getString("token","").toString()

        val r = AuthentificatedRequest(
            Request.Method.DELETE,
            url,
            bearerToken,
            Response.Listener {

            },
            Response.ErrorListener {
                println(bearerToken)
            }
        )
        queue.add(r)
    }


}