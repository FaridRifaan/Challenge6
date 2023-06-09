@file:Suppress("RedundantNullableReturnType")

package com.binar.challenge6.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.challenge6.R
import com.binar.challenge6.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@Suppress("RedundantNullableReturnType")
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var sharedPrefRegis : SharedPreferences
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        sharedPrefRegis = requireContext().getSharedPreferences("REGISTERUSER", Context.MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {
            register()
        }

    }



    private fun register(){
        val createUsername = binding.etUsernameRegister.text.toString()
        val createEmail = binding.etEmailRegister.text.toString()
        val createPass = binding.etPasswordRegister.text.toString()
        val createRepeatPass = binding.etKonfirmPasswordRegister.text.toString()

        val addUser = sharedPrefRegis.edit()
        addUser.putString("USER", createUsername)

        if (createUsername.isNotEmpty() && createEmail.isNotEmpty() && createPass.isNotEmpty() && createRepeatPass.isNotEmpty()) {
            if (createPass == createRepeatPass) {
                // Register user with email and password
                addUser.apply()
                auth.createUserWithEmailAndPassword(createEmail, createPass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // User registered successfully
                            // Navigate to the login screen
                            Toast.makeText(context, "Berhasil Register", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            // Show error message if registration fails
                            Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Show error message if passwords do not match
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Show error message if any field is empty
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }

    }


}