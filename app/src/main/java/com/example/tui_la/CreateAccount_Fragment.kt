package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccount_Fragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_createaccount, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val usernameEditText: EditText = view.findViewById(R.id.un_editText)
        val emailEditText: EditText = view.findViewById(R.id.email_editText)
        val passwordEditText: EditText = view.findViewById(R.id.pw_editText)
        val createAccountButton: Button = view.findViewById(R.id.ca_button)
        val loginTextView: TextView = view.findViewById(R.id.textView6)

        createAccountButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || !email.contains("@") || password.isEmpty() || password.length < 6) {
                Toast.makeText(activity, "Invalid username, email, or password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user in Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // Save user information to Realtime Database
                            val user = hashMapOf("username" to username, "email" to email)
                            database.getReference("users")
                                .child(userId)
                                .setValue(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show()

                                        // Navigate to the login fragment
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.coordinatorLayout, LogIn_Fragment())
                                            .commit()
                                    } else {
                                        Toast.makeText(activity, "Failed to save user info.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(activity, "Failed to get user ID.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val exception = task.exception
                        Toast.makeText(activity, "Failed to create account: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        loginTextView.setOnClickListener {
            // Navigate to the login fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.coordinatorLayout, LogIn_Fragment())
                .commit()
        }

        return view
    }
}
