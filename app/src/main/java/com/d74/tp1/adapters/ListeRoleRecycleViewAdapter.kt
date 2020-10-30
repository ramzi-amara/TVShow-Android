package com.d74.tp1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d74.tp1.R
import com.example.tp1.models.Role
import com.squareup.picasso.Picasso

class ListeRoleRecycleViewAdapter(private val listeRole: MutableList<Role>) :
    RecyclerView.Adapter<ListeRoleRecycleViewAdapter.RoleViewHolder>() {

    class RoleViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.role_item, parent, false) as View
        return RoleViewHolder(view)
    }

    override fun onBindViewHolder(holderRole: RoleViewHolder, position: Int) {
        val imgRole = holderRole.view.findViewById<ImageView>(R.id.img_Role)
        val name = holderRole.view.findViewById<TextView>(R.id.txt_Name)
        val roleName = holderRole.view.findViewById<TextView>(R.id.txt_Role)
        Picasso.get().load(this.listeRole[position].Image).into(imgRole)
        name.text = this.listeRole[position].Name
        roleName.text = this.listeRole[position].Character
        println(this.listeRole[position].Character)

    }
    override fun getItemCount() = this.listeRole.size
}