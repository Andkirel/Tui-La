package com.example.tui_la

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private var communicator: ICommunicatorLogin? = null
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeCheckBox: CheckBox

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ICommunicatorLogin) {
            communicator = context
        } else {
            throw RuntimeException("$context must implement Interface for Login")
        }
        sharedPreferences = context.getSharedPreferences("YourSharedPreferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_login, container, false)
        auth = FirebaseAuth.getInstance()

        emailEditText = view.findViewById(R.id.usname)
        passwordEditText = view.findViewById(R.id.password)
        rememberMeCheckBox = view.findViewById(R.id.checkBox)
        val loginButton = view.findViewById<Button>(R.id.Button_login_loginButton)
        val backButtonLogin: ImageButton = view.findViewById(R.id.Backbutton_login)

        loadCredentialsIfRemembered()

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
                        if (rememberMeCheckBox.isChecked) {
                            saveCredentials(email, password)
                        } else {
                            clearCredentials()
                        }
                        communicator?.passDataToFragment(uid, "AnySecondaryDataYouWantToPass")
                    } else {
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        backButtonLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LandingFragment())
                .commit()
        }

        return view
    }

    private fun saveCredentials(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("Email", email)
        editor.putString("Password", password)
        editor.putBoolean("RememberMe", true)
        editor.apply()
    }

    private fun clearCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("Email")
        editor.remove("Password")
        editor.putBoolean("RememberMe", false)
        editor.apply()
    }

    private fun loadCredentialsIfRemembered() {
        val rememberMe = sharedPreferences.getBoolean("RememberMe", false)
        if (rememberMe) {
            val savedEmail = sharedPreferences.getString("Email", "")
            val savedPassword = sharedPreferences.getString("Password", "")
            emailEditText.setText(savedEmail)
            passwordEditText.setText(savedPassword)
            rememberMeCheckBox.isChecked = true
        }
    }

    override fun onDetach() {
        super.onDetach()
        communicator = null
    }
}