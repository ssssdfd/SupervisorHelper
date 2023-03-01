package com.ssssdfd.supervisorhelperapp

import android.os.Bundle
import android.transition.Fade
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ssssdfd.supervisorhelperapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        enterTransition = Fade()
        exitTransition = Fade()
        returnTransition = Fade()
        reenterTransition = Fade()
        val x = auth.currentUser
        if(x == null){
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<LogInFragment>(R.id.maContainer)
            }
        }else{
            binding.mfragTV.text = x.email
        }

        binding.mfragSinOutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<LogInFragment>(R.id.maContainer)
            }
        }
        binding.mfragCamera.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<CameraFragment>(R.id.maContainer)
            }
        }
    }



}