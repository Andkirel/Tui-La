package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class Add_payment: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_addpaymentmethod)

        val Continue = findViewById<TextView>(R.id.Button_AP_Continue)
        Continue.setOnClickListener {
            val Intent = Intent(this, LogInActivity::class.java)
            startActivity(Intent)
        }
    }
}