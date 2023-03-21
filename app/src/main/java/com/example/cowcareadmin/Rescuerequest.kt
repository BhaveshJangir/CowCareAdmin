package com.example.cowcareadmin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cowcareadmin.databinding.FragmentRescuerequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Rescuerequest : Fragment() {
    private var _binding: FragmentRescuerequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var Adapter: ChatAdapter
    lateinit var mdatabase: DatabaseReference
    private var messagelist = ArrayList<ChatModel>()
    var receiverroom: String? = null
    var senderroom: String? = null
    var senderuid="User"
    var receiveruid="Admin"
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentRescuerequestBinding.inflate(inflater, container, false)
            val root: View = binding.root
//            receiveruid=""
//            senderuid= FirebaseAuth.getInstance().currentUser?.uid
            mdatabase= FirebaseDatabase.getInstance().reference
            Adapter= activity?.applicationContext?.let { ChatAdapter(it,messagelist) }!!
            senderroom=senderuid+receiveruid
            receiverroom=receiveruid+senderuid
            savingchattodatabase()
            showdatainrv()
            return root

        }

    fun showdatainrv(){
        mdatabase.child("chat").child(senderroom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for(e in snapshot.children){
                        val message=e.getValue(ChatModel::class.java)
                        messagelist.add(message!!)
//                        Log.d("sand", message.message.toString())
                    }
                    Adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        binding.chatrecyclerview.layoutManager=LinearLayoutManager(activity)
        binding.chatrecyclerview.adapter=Adapter
    }

    fun savingchattodatabase(){
        binding.sendbutton.setOnClickListener {
            val message=binding.Message.editText!!.text.toString()
            val messageobject= ChatModel(message,senderuid)
            mdatabase.child("chat").child(senderroom!!).child("message").push()
                .setValue(messageobject).addOnSuccessListener {
                    mdatabase.child("chat").child(receiverroom!!).child("message").push()
                        .setValue(messageobject)
                }
            binding.Message.editText!!.setText("")
        }
    }
}