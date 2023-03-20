package com.example.cowcareadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cowcareadmin.databinding.FragmentHomeBinding

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var Adapter: Home_Adapter
    lateinit var mdatabase: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setuprecyclerview()
        return root
    }

    private fun setuprecyclerview() {
        mdatabase= FirebaseDatabase.getInstance().reference.child("rescue")
        val options: FirebaseRecyclerOptions<Posts> = FirebaseRecyclerOptions.Builder<Posts>()
            .setQuery(mdatabase, Posts::class.java)
            .build()
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd=true
//        binding.homeRecylerview.setHasFixedSize(true)
        binding.homeRecylerview.layoutManager=linearLayoutManager
        Adapter= Home_Adapter(options)
        binding.homeRecylerview.adapter=Adapter
    }

    override fun onStart() {
        super.onStart()
        Adapter.startListening()
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    override fun onStop() {
        super.onStop()
        Adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}