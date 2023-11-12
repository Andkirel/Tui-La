package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tui_la.R

class B_E_options: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_b_e_options)

        val onemin = findViewById<Button>(R.id.Button_GB_1min)
        onemin.setOnClickListener {
            val Intent = Intent(this, B_E_1Min::class.java)
            startActivity(Intent)
        }
        val threemin = findViewById<Button>(R.id.Button_GB_3min)
        threemin.setOnClickListener {
            val Intent = Intent(this, B_E_3Min::class.java)
            startActivity(Intent)
        }
        val fivemin = findViewById<Button>(R.id.Button_GB_5min)
        fivemin.setOnClickListener {
            val Intent = Intent(this, B_E_5Min::class.java)
            startActivity(Intent)
        }
        val tenmin = findViewById<Button>(R.id.Button_GB_10min)
        tenmin.setOnClickListener {
            val Intent = Intent(this, B_E_options::class.java)
            startActivity(Intent)
        }
        val INF = findViewById<Button>(R.id.Button_GB_Infinite)
        INF.setOnClickListener {
            val Intent = Intent(this, B_E_Infinite::class.java)
            startActivity(Intent)
        }
        val Nav_GB = findViewById<ImageButton>(R.id.ImageButton_GB_Nav)
        Nav_GB.setOnClickListener {
            val Intent = Intent(this, Nav_menu::class.java)
            startActivity(Intent)
        }
        val settings_gb = findViewById<ImageButton>(R.id.ImageButton_GB_Settings)
        settings_gb.setOnClickListener {
            val Intent = Intent(this, Settings_menu::class.java)
            startActivity(Intent)
        }

    }
}