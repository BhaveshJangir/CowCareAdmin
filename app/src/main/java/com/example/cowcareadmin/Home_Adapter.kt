package com.example.cowcareadmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso

class Home_Adapter(options: FirebaseRecyclerOptions<Posts>) : FirebaseRecyclerAdapter<Posts, Home_Adapter.HomeViewholder>(
    options
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HomeViewholder {
        return HomeViewholder(LayoutInflater.from(parent.context).inflate(R.layout.post_design, parent, false))
    }

    override fun onBindViewHolder(holder:HomeViewholder, position: Int, model: Posts) {
        holder.Date.text=model.date
        holder.Title.text=model.content
        if(model.url?.isEmpty() == false){
            Picasso.get().load(model.url).into(holder.image)
        }
    }
    class HomeViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val Date : TextView = itemView.findViewById(R.id.date)
        val Title: TextView =itemView.findViewById(R.id.title)
        val image: ImageView =itemView.findViewById(R.id.post_image)
    }

}