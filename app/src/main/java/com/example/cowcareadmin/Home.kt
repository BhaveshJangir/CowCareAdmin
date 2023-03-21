package com.example.cowcareadmin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cowcareadmin.databinding.FragmentHomeBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.IOException

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var url=""
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        upload(it)
        //   binding.MemberImage.setImageURI(it)
        getImageUri()

        //   FirebaseDatabase.getInstance().getReference().child("Autheticate_Users").child(uid).child("url").setValue(ur)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.UserImage.setOnClickListener {
            contract.launch("image/*")
            binding.textView.visibility=View.GONE
        }
        binding.Post.setOnClickListener {
            val content=binding.content.editText?.text.toString()
            val date=binding.Date.editText?.text.toString()
            FirebaseDatabase.getInstance().reference.child("rescue").push().setValue(Posts(content,date, url))
        }
        return root
    }
    private fun upload(imageUri: Uri?) {
        try{
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,imageUri)
            var byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream)

            var byte = byteArrayOutputStream.toByteArray()
//            FirebaseDatabase.getInstance().reference.child("rescue").push().setValue(Posts())
            FirebaseStorage.getInstance().getReference().child("rescue").putBytes(byte)


            val bmp: Bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
            if(bmp.toString().isEmpty() == false){
                binding.UserImage.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bmp,
                        binding.UserImage.getWidth(),
                        binding.UserImage.getHeight(),
                        false
                    )
                )
            }


        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
    private fun getImageUri()  {
        FirebaseStorage.getInstance().reference.child("rescue").downloadUrl.addOnSuccessListener {
            url = it.toString()
        }.addOnFailureListener {
            Toast.makeText(context, "Image Link Failed", Toast.LENGTH_SHORT).show()
        }
    }
}