package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@UnstableApi class JournalTableActivity : AppCompatActivity() {

    private lateinit var firebaseReference: DatabaseReference
    private lateinit var uid: String

    private lateinit var journalRecyclerView: RecyclerView

    private lateinit var backButton: ImageButton

    // implement clearing on logout
    private lateinit var journalArrayList: ArrayList<JournalData>
    // holds journal identifier keys
    private lateinit var entryKeyList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_table)

        uid = intent.getStringExtra("UID") ?: return

        firebaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Journal")

        // getting the recyclerview by its id
        journalRecyclerView = findViewById(R.id.rvJournal)

        // back button to home activity
        backButton = findViewById(R.id.backButton)

        // creates a vertical layout Manager
        journalRecyclerView.layoutManager = LinearLayoutManager(this)

        // initialize lists
        journalArrayList = arrayListOf<JournalData>()
        entryKeyList = arrayListOf<String>()

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            backToHome()
        }

        // get data; pull from firebase
        getUserData()
    }

    private fun getUserData() {
        firebaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                journalArrayList.clear()
                if (snapshot.exists()) {
                    for (key in snapshot.children) {
                        val userData = key.getValue(JournalData::class.java)
                        journalArrayList.add(userData!!)
                        entryKeyList.add(key.key.toString())
                    }
                    // Setting the adapter to the recyclerview
                    val jAdapter = JournalTableAdapter(journalArrayList)
                    journalRecyclerView.adapter = jAdapter

                    // Makes recycler view items clickable
                    // Takes user to a page with the information of the clicked entry
                    jAdapter.setOnItemClickListener(object : JournalTableAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val journalWrite = Intent(this@JournalTableActivity, JournalWriteActivity::class.java)

                            journalWrite.putExtra("journalTitle", journalArrayList[position].title)
                            journalWrite.putExtra("journalEntry", journalArrayList[position].entry)
                            journalWrite.putExtra("journalId", entryKeyList[position])
                            journalWrite.putExtra("journalTime", journalArrayList[position].time)
                            journalWrite.putExtra("journalDate", journalArrayList[position].date)
                            journalWrite.putExtra("journalEmotion", journalArrayList[position].emotion)

                            startActivity(journalWrite)
                        }
                        override fun deleteEntry(position: Int) {
                            firebaseReference.child(entryKeyList[position]).removeValue()
                                .addOnSuccessListener{
                                    Toast.makeText(this@JournalTableActivity,"Entry deleted",Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener{err ->
                                    Toast.makeText(this@JournalTableActivity, "Error ${err.message}", Toast.LENGTH_LONG).show()
                                }
                            getUserData()
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addEntry(view: View) {
        val journalWrite = Intent(this@JournalTableActivity, JournalWriteActivity::class.java)

        startActivity(journalWrite)
    }

    private fun backToHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("UID", uid) // Adding the UID to the intent
        startActivity(homeIntent)
    }
}