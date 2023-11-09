package com.example.tui_la

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import android.content.Context


class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var communicator: ICommunicatorLogin? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ICommunicatorLogin) {
            communicator = context
        } else {
            throw RuntimeException("$context must implement Interface for Login")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, 
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_login, container, false)
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val emailEditText = view.findViewById<EditText>(R.id.usname)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Basic validation
            if (email.isEmpty() || !email.contains("@") || password.isEmpty() || password.length < 6) {
                Toast.makeText(activity, "Invalid email or password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        // Using the interface to pass UID to the activity
                        communicator?.passDataToFragment(uid, "AnySecondaryDataYouWantToPass")
                    } else {
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        communicator = null
    }
}
