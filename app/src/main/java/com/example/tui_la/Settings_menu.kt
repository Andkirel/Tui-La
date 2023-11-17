package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tui_la.R

class Settings_menu: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_settings)

        val stickers = findViewById<Button>(R.id.button_stickers)
        stickers .setOnClickListener {
            val Intent = Intent(this, BadgesActivity::class.java)
            startActivity(Intent)
        }



    }
}