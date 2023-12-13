package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccountFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_createaccount, container, false)

        // Initialize Firebase Auth and Realtime Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize views
        val usernameEditText: EditText = view.findViewById(R.id.un_editText)
        val emailEditText: EditText = view.findViewById(R.id.email_editText)
        val passwordEditText: EditText = view.findViewById(R.id.pw_editText)
        val createAccountButton: Button = view.findViewById(R.id.Button_Createaccount)
        val loginTextView: TextView = view.findViewById(R.id.Login_text)
        val backButtonCreateAccount: ImageButton = view.findViewById(R.id.Backbutton_createaccount)

        createAccountButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Basic validation
            if (username.isEmpty() || email.isEmpty() || !email.contains("@") || password.isEmpty() || password.length < 6) {
                Toast.makeText(activity, "Invalid username, email, or password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user in Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // Save user information to Realtime Database
                            val user = hashMapOf("username" to username, "email" to email)
                            database.getReference("users")
                                .child(userId)
                                .setValue(user)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show()

                                        // Navigate to the login fragment
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.fragmentContainer, LogInFragment())
                                            .commit()
                                    } else {
                                        Toast.makeText(activity, "Failed to save user info.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(activity, "Failed to get user ID.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val exception = authTask.exception
                        Toast.makeText(activity, "Failed to create account: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        loginTextView.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LogInFragment())
                .commit()
        }

        backButtonCreateAccount.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LandingFragment())
                .commit()
        }

        return view
    }
}