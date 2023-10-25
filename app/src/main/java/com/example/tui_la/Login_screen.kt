package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Login_screen:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        val loginback = findViewById<ImageButton>(R.id.Backbutton_login)
        loginback.setOnClickListener {
            val Intent = Intent(this, Landing::class.java)
            startActivity(Intent)
        }
        val login = findViewById<Button>(R.id.Button_login_loginButton)
        login.setOnClickListener {
            val Intent = Intent(this, Emotions::class.java)
            startActivity(Intent)
        }
    }

}