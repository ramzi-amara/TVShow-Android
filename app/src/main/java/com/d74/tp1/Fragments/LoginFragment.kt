package com.d74.tp1.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.d74.tp1.R
import com.d74.tp1.models.AuthentificationReponse
import com.google.gson.Gson
import layout.AuthentificationRequest


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflatedView= inflater.inflate(R.layout.fragment_login, container, false)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val txtEmail=inflatedView.findViewById<EditText>(R.id.txtEmail)
        txtEmail.setText(sharedPref.getString("username",""))
        val txtPassword=inflatedView.findViewById<EditText>(R.id.txtPassword)
        txtPassword.setText(sharedPref.getString("password",""))

        val btnLog=inflatedView.findViewById<Button>(R.id.btnLogin)
        btnLog.setOnClickListener {
            authentification()

        }

        return  inflatedView
    }
    private fun authentification(){
        val queue = Volley.newRequestQueue(this.context)
        val url="http://tvshowapi.sv55.cmaisonneuve.qc.ca/token"
        val txtEmail:EditText=this.requireView().findViewById(R.id.txtEmail)
        val txtPassword:EditText=this.requireView().findViewById(R.id.txtPassword)
        val username=txtEmail.text.toString()
        val password=txtPassword.text.toString()

        val r = AuthentificationRequest(
            Request.Method.POST,
            url,
            username,
            password,
            Response.Listener {
                val gson=Gson()
                val reponse=gson.fromJson(it, AuthentificationReponse::class.java)
                println(reponse)
                sharedPref.edit().putString("token", reponse.access_token).apply()
                Toast.makeText(activity, "Login success ", Toast.LENGTH_SHORT).show()
                sharedPref.edit().putString("username", username).apply()
                sharedPref.edit().putString("password", password).apply()
                this.requireView().findNavController().navigate(R.id.tvShowsFragment)

            },
            Response.ErrorListener {
                val toast = Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT)
                toast.show()
                sharedPref.edit().putString("username", "").apply()
                sharedPref.edit().putString("password", "").apply()

            }
        )
        queue.add(r)
    }


}