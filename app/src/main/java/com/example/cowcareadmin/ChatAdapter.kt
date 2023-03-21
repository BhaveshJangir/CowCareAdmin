package com.example.cowcareadmin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter (val context: Context,val messagelist: ArrayList<ChatModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val Item_receive=1
    val Item_send=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==Item_receive){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return recieveviewholder(view)
        }
        else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentviewholder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage=messagelist[position]
        if (holder.javaClass==sentviewholder::class.java){
            val viewholder=holder as sentviewholder
            viewholder.sentmessage.text=currentmessage.message
        }
        else{
            val viewholder=holder as recieveviewholder
            viewholder.receivemessage.text=currentmessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage=messagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.sender))
            return Item_send
        else
            return Item_receive
    }
    override fun getItemCount(): Int {
        return messagelist.size
    }
    class sentviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentmessage: TextView =itemView.findViewById(R.id.sendmessage)
    }
    class recieveviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivemessage: TextView =itemView.findViewById(R.id.receivemessage)
    }
}