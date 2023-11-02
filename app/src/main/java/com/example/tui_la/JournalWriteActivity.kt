package com.example.tui_la

import android.content.Intent
import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.util.Calendar

class JournalWriteActivity : AppCompatActivity() {
    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private lateinit var currentDate: String
    private lateinit var currentTime: String

    private var journalEmotion: Int = 0

    private var entryKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        // firebase authentication
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
        firebaseReference = Firebase.database.reference

        // set the fields using firebase data if available
        setJournalData()
    }

    fun save(view: View) {
        if (entryKey.isBlank()) {

            // get all of the fields that have data
            journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
            journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
            /*journalEmotion = findViewById<ImageView>(R.id.journalWriteEmotion)*/

            // gets the calendar date and time
            val calendar = Calendar.getInstance().time
            currentDate = DateFormat.getDateInstance().format(calendar)
            currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

            writeNewJournalData(
                auth.uid!!,
                journalTitle,
                currentTime,
                currentDate,
                journalEntry/*,journalEmotion*/
            )
        } else {

            journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
            journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
            /*journalEmotion = findViewById<ImageView>(R.id.journalWriteEmotion)*/

            updateEntry(auth.uid!!, journalTitle, journalEntry/*,journalEmotion*/)
        }
    }

    private fun writeNewJournalData(userId: String, title: String, time: String, date: String, entry: String/*, emotion: DrawableRes*/){
        val data = JournalData(title,time,date,entry,0)

        val journalId = firebaseReference.push().key!!
        
        firebaseReference.child("users").child(userId).child("Journal").child(journalId).setValue(data)
            .addOnCompleteListener{
                Toast.makeText(this, "Entry saved", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun setJournalData() {

        if (!(intent.getStringExtra("journalId").isNullOrBlank()))   {

            entryKey = intent.getStringExtra("journalId")!!
            currentDate = intent.getStringExtra("journalDate")!!
            currentTime = intent.getStringExtra("journalTime")!!

            findViewById<TextView>(R.id.journalWriteEntryTitle).text = intent.getStringExtra("journalTitle")
            findViewById<TextView>(R.id.journalWriteEntry).text = intent.getStringExtra("journalEntry")
            /*findViewById<ImageView>(R.id.journalWriteEmotion).drawable*/


        }
    }

    fun back(view: View) {
        val journalTable = Intent(this, JournalTableActivity::class.java)

        startActivity(journalTable)
    }

    private fun updateEntry(userId: String, title: String, entry: String/*, emotion: DrawableRes*/) {
        val data = JournalData(title,currentTime,currentDate,entry,0)

        firebaseReference.child("users").child(userId).child("Journal").child(entryKey).setValue(data)
            .addOnCompleteListener{
                Toast.makeText(this, "Entry updated", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun delete(){

    }
}