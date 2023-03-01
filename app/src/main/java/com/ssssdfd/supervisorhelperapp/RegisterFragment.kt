package com.ssssdfd.supervisorhelperapp

import android.os.Bundle
import android.text.TextUtils
import android.transition.Fade
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.ssssdfd.supervisorhelperapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MainFragment>(R.id.maContainer)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        enterTransition = Fade()
        exitTransition = Fade()
        returnTransition = Fade()
        reenterTransition = Fade()
        binding.regLogInTV.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<LogInFragment>(R.id.maContainer)
            }
        }
        binding.regGoBtn.setOnClickListener{
            registration()
        }
    }

    private fun registration(){
        val email = binding.regEmailEdt.text.toString()
        val password = binding.regPasswordEdt.text.toString()

        if (TextUtils.isEmpty(email)){
            binding.regEmailEdt.error = "Fill field"
        }
        if (TextUtils.isEmpty(password)){
            binding.regPasswordEdt.error = "Fill field"
        }
        if (email.isNotEmpty() && password.isNotEmpty()){
            Toast.makeText(requireContext(), "Is not empty", Toast.LENGTH_SHORT).show()
            binding.regProgressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information )
                        Toast.makeText(requireContext(), "Authentication success.", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<MainFragment>(R.id.maContainer)
                        }
                        binding.regProgressBar.visibility = View.GONE
                    } else {
                        // If sign in fails, display a message to the user. ()
                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                        Log.d("TAAG","Exception:${task.exception}")
                        binding.regProgressBar.visibility = View.GONE
                    }
                }
        }
    }
}