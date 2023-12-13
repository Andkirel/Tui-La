package com.example.tui_la

import android.content.Intent
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import java.util.Calendar

@UnstableApi
class JournalWriteActivity : AppCompatActivity() {

    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var uid: String

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private lateinit var currentDate: String
    private lateinit var currentTime: String
    private lateinit var journalEmotion: String

    private lateinit var emotionSpinner: Spinner
    private lateinit var setImage: ImageView

    private var entryKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email,password)
        firebaseReference = Firebase.database.reference

        // set up spinner/drop down menu
        setJournalSpinner()

        // set the fields using data if available
        setJournalData()
    }
    fun save(view: View) {
        journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
        journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
        // journalEmotion is set by the onItemSelected function of the spinner

        val calendar = Calendar.getInstance().time
        currentDate = DateFormat.getDateInstance().format(calendar)
        currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

        if (entryKey.isBlank()) {
            // gets the calendar date and time
//            val calendar = Calendar.getInstance().time
//            currentDate = DateFormat.getDateInstance().format(calendar)
//            currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

            writeNewJournalData(
                auth.uid!!,
                journalTitle,
                currentTime,
                currentDate,
                journalEntry,
                journalEmotion
            )
        } else {
            updateEntry(auth.uid!!, journalTitle, journalEntry,journalEmotion)
        }
    }
    private fun writeNewJournalData(userId: String, title: String, time: String, date: String, entry: String, emotion: String){
        val data = JournalData(title,time,date,entry,emotion)
        val journalId = firebaseReference.push().key!!

        firebaseReference.child("users").child(userId).child("Journal").child(journalId).setValue(data)
            .addOnCompleteListener{
                Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun setJournalSpinner() {
        emotionSpinner = findViewById(R.id.journalWriteSpinner)
        emotionSpinner.adapter = JournalSpinnerAdapter(HMData.list!!, this)

        emotionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)

                journalEmotion = HMData.list!![position].name
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    private fun setJournalData() {
        if (!(intent.getStringExtra("journalId").isNullOrBlank()))   {

            entryKey = intent.getStringExtra("journalId")!!
            currentDate = intent.getStringExtra("journalDate")!!
            currentTime = intent.getStringExtra("journalTime")!!

            findViewById<TextView>(R.id.journalWriteEntryTitle).text = intent.getStringExtra("journalTitle")
            findViewById<TextView>(R.id.journalWriteEntry).text = intent.getStringExtra("journalEntry")

            val imgString = intent.getStringExtra("journalEmotion")
            val imgInt = HMData.getValue(imgString!!)

            // take key/value, turn into index for spinner
            val index = HMData.list!!.indexOf(JournalHashMap(imgString,imgInt))

            emotionSpinner.setSelection(index)
        }
    }
    fun back(view: View) {
        val journalTable = Intent(this, JournalTableActivity::class.java)
        startActivity(journalTable)
    }
    private fun updateEntry(userId: String, title: String, entry: String, emotion: String) {
        val data = JournalData(title,currentTime,currentDate,entry,emotion)

        firebaseReference.child("users").child(userId).child("Journal").child(entryKey).setValue(data)
            .addOnCompleteListener{
                Toast.makeText(this, "Entry updated", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}