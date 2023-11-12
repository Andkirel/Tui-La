package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class Payment: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_payment)

        val Visa = findViewById<TextView>(R.id.Button_payment_Visa)
        Visa.setOnClickListener {
            val Intent = Intent(this, Add_payment::class.java)
            startActivity(Intent)
        }
    }
}