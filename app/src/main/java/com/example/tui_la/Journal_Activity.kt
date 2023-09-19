package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tui_la.ui.journal.JournalFragment

class Journal_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JournalFragment.newInstance())
                .commitNow()
        }
    }
}