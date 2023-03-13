package com.ssssdfd.supervisorhelperapp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.ssssdfd.supervisorhelperapp.databinding.FragmentTestBinding


class TestFragment : Fragment(R.layout.fragment_test) {
    private lateinit var binding: FragmentTestBinding
    val SELECT_IMAGE_REQUEST_CODE = 1
    val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
            binding.myImageView.setImageURI(it)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestBinding.bind(view)


        binding.addImageBtn.setOnClickListener {
           pickImage.launch("image/*")
        }
    }


}