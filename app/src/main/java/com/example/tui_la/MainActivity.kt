package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signinbutton = findViewById<Button>(R.id.Button_Signin)
        signinbutton.setOnClickListener{
            val Intent = Intent(this, Login_screen::class.java)
            startActivity(Intent)
        }
        val createaccount = findViewById<TextView>(R.id.Create_account)
        createaccount.setOnClickListener {
            val Intent = Intent(this, Create_Account::class.java)
            startActivity(Intent)
        }
    }
}