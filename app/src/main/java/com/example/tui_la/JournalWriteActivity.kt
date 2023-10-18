package com.example.tui_la

import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class JournalWriteActivity : AppCompatActivity() {
    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private var journalEmotion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        // firebase authentication
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
        firebaseReference = Firebase.database.reference

        // set the fields using firebase data if available

    }

    fun save(view: View) {
        // get all of the fields that have data
        journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
        journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
        /*journalEmotion = findViewById<ImageView>(R.id.journalWriteEmotion)*/

        // gets the calendar date and time
        val calendar = Calendar.getInstance().time
        val currentDate = DateFormat.getDateInstance().format(calendar)
        val currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        writeNewJournalData(auth.uid!!, journalTitle, currentTime,currentDate, journalEntry/*,journalEmotion*/)
    }

    private fun writeNewJournalData(userId: String, title: String, time: String, date: String, entry: String/*, emotion: DrawableRes*/){
        val data = JournalData(title,time,date,entry,0)
        
        firebaseReference.child("users").child(userId).child("Journal").setValue(data)
            .addOnCompleteListener{

            }
            .addOnFailureListener{

            }
    }


    fun cancel(view: View) {

    }
}