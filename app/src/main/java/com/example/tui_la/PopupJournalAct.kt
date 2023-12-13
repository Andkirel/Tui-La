package com.example.tui_la

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.zip.Inflater


class PopupJournalAct: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_badges_journal)

        val window = PopupWindow(this)
        val view = Inflater.Inflater(R.layout.layout_monthone, null)
        window.contentview = view
        val popup = findViewById<Button>(R.id.Button_1stJournal)
        popup.setOnClickListener{
            window.dismiss()
        }



    }


}