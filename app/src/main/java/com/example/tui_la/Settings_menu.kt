package com.example.tui_la

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi
import com.example.tui_la.databinding.FragmentcontainerBinding

@UnstableApi class Settings_menu: AppCompatActivity() {


    private lateinit var binding: FragmentcontainerBinding

    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentcontainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the EmotionsFragment without adding it to the back stack
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, settingsfragment())
                .commit()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        uid = intent.getStringExtra("UID") ?: return

        val backButton = findViewById<ImageButton>(R.id.Backbutton_settings)
        backButton.setOnClickListener {
            backToHome()
        }
    }
    private fun backToHome() {
        onBackPressed()
    }
}