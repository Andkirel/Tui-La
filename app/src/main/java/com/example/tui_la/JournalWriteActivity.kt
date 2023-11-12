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
    private lateinit var uId: String
    private lateinit var firebaseReference: DatabaseReference

    private lateinit var journalTitle: String
    private lateinit var journalEntry: String
    private lateinit var currentDate: String
    private lateinit var currentTime: String
    private var journalEmotion: Int = 0

    private lateinit var emotionSpinner: Spinner
    private lateinit var setImage: ImageView

    //private val emoteMap = EmotionMap()

    private var entryKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_write)

        // firebase authentication
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
        firebaseReference = Firebase.database.reference

        // spinner layout
        setJournalSpinner()

        // set the fields using firebase data if available
        setJournalData()
    }

    fun save(view: View) {
        journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
        journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
        //journalEmotion findViewById<ImageView>(R.id.journalWriteEmotion)

            //Icon.Afraid.getValue()

        if (entryKey.isBlank()) {

           /* // get all of the fields that have data
            journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
            journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
            journalEmotion = emoteMap.getDrawableKey(findViewById<ImageView>(R.id.journalWriteEmotion).drawable.toString())
*/
            // gets the calendar date and time
            val calendar = Calendar.getInstance().time
            currentDate = DateFormat.getDateInstance().format(calendar)
            currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

            writeNewJournalData(
                auth.uid!!,
                journalTitle,
                currentTime,
                currentDate,
                journalEntry,
                0
            )
        } else {
            /*journalTitle = findViewById<TextView>(R.id.journalWriteEntryTitle).text.toString()
            journalEntry = findViewById<TextView>(R.id.journalWriteEntry).text.toString()
            journalEmotion = emoteMap.getDrawableKey(findViewById<ImageView>(R.id.journalWriteEmotion).drawable.toString())
*/
            updateEntry(auth.uid!!, journalTitle, journalEntry,0)
        }
    }

    private fun writeNewJournalData(userId: String, title: String, time: String, date: String, entry: String, emotion: Int){
        val data = JournalData(title,time,date,entry,emotion)

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

            //findViewById<ImageView>(R.id.journalWriteEmotion).setBackgroundResource(R.drawable.happy)

            // hard code functionality
            //findViewById<ImageView>(R.id.journalWriteEmotion).setImageResource(R.drawable.happy)

            // enum enabled, needs more code for variability
            //findViewById<ImageView>(R.id.journalWriteEmotion).setImageResource(Icon.Happy.resourceId)

            // fully variable support with hashmap keys/values
            /*findViewById<ImageView>(R.id.journalWriteEmotion).setImageResource(
                emoteMap.getDrawableValue(intent.getIntExtra("journalEmotion",0)))*/

        }
    }

    fun back(view: View) {
        val journalTable = Intent(this, JournalTableActivity::class.java)

        startActivity(journalTable)
    }

    private fun updateEntry(userId: String, title: String, entry: String, emotion: Int) {
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

    private fun setJournalSpinner() {
        emotionSpinner = findViewById(R.id.journalWriteSpinner)
        //val adapter = JournalSpinnerAdapter(Emotions.list!!,this)

        //emotionSpinner.adapter = adapter

        emotionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}