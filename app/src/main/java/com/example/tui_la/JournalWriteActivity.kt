package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import java.util.Calendar;

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class JournalWriteActivity : AppCompatActivity() {

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private var journalEmotion: Int = 0
    private lateinit var currentDate: String

    private lateinit var firebaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        // set the fields using firebase data if available

        firebaseReference = FirebaseDatabase.getInstance().getReference("Users")
    }

    fun save(view: View) {
        // get all of the fields that have data
        journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).toString()
        journalEntry = findViewById<TextView>(R.id.journalWriteEntry).toString()
        journalEmotion /*= findViewById<>(R.id.journalWriteEmotion)*/

        // gets the calendar date
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // formats: "year/month/day"
        val currentDate = year.toString().plus('/') + month.toString().plus('/') + day.toString().plus('/')
        val currentTime = Calendar.getInstance().time.toString()

        val Id = firebaseReference.push().key!!
        val info = JournalData(journalTitle,currentTime,currentDate, journalEntry,journalEmotion)

        firebaseReference.child(Id).setValue(info)
            .addOnCompleteListener{

            }
            .addOnFailureListener{

            }
    }

    fun cancel(view: View) {

    }
}