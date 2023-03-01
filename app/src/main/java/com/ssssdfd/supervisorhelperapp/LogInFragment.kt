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
import com.ssssdfd.supervisorhelperapp.databinding.FragmentLogInBinding

class LogInFragment : Fragment(R.layout.fragment_log_in) {
    private lateinit var binding: FragmentLogInBinding
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
        binding = FragmentLogInBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        enterTransition = Fade()
        exitTransition = Fade()
        returnTransition = Fade()
        reenterTransition = Fade()
        binding.flRegisterTV.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<RegisterFragment>(R.id.maContainer)
            }
        }
        binding.flGoBtn.setOnClickListener {
            logIn()
        }
    }

    private fun logIn(){
        val email = binding.flEmailEdt.text.toString()
        val password = binding.flPasswordEdt.text.toString()

        if (TextUtils.isEmpty(email)){
            binding.flEmailEdt.error = "Fill field"
        }
        if (TextUtils.isEmpty(password)){
            binding.flPasswordEdt.error = "Fill field"
        }
        if (email.isNotEmpty() && password.isNotEmpty()){
            Toast.makeText(requireContext(), "Is not empty", Toast.LENGTH_SHORT).show()
            binding.flProgressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information )
                        Toast.makeText(requireContext(), "signInWithEmail:success", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<MainFragment>(R.id.maContainer)
                        }
                        binding.flProgressBar.visibility = View.GONE
                    } else {
                        // If sign in fails, display a message to the user. ()
                        Toast.makeText(requireContext(), "signInWithEmail:failure", Toast.LENGTH_SHORT).show()
                        Log.d("TAAG","Exception:${task.exception}")
                        binding.flProgressBar.visibility = View.GONE
                    }
                }
        }
    }
}