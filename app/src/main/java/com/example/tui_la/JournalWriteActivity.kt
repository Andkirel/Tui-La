package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar;

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JournalWriteActivity : AppCompatActivity() {

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private var journalEmotion: Int = 0
    private lateinit var currentDate: String
   /* private lateinit var auth: FirebaseAuth*/

    private lateinit var firebaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        // set the fields using firebase data if available
/*
        auth = FirebaseAuth.getInstance()*/
        firebaseReference = Firebase.database.reference
    /*Database.getInstance().getReference("users")*/
    }

    fun save(view: View) {
        // get all of the fields that have data
        journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).toString()
        journalEntry = findViewById<TextView>(R.id.journalWriteEntry).toString()
      /*  journalEmotion = findViewById<ImageView>(R.id.journalWriteEmotion)*/

        // gets the calendar date
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // formats: "year/month/day"
        val currentDate = year.toString().plus('/') + month.toString().plus('/') + day.toString().plus('/')
        val currentTime = Calendar.getInstance().time.toString()

        val Id = firebaseReference.push().key!!
        val info = JournalData(journalTitle,currentTime,currentDate, journalEntry, 0/*,journalEmotion*/)


        writeNewJournalData("EdTest",journalTitle, currentTime,currentDate, journalEntry/*,journalEmotion*/)
    }

    private fun writeNewJournalData(userId: String, title: String, time: String, date: String, entry: String/*, emotion: DrawableRes*/){
        val data = JournalData(title,time,date,entry,0)

        firebaseReference.child("data").child("Journal").child(userId).setValue(data)
            .addOnCompleteListener{

            }
            .addOnFailureListener{

            }
    }


    fun cancel(view: View) {

    }
}