package com.example.tui_la

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class JournalTableActivity : AppCompatActivity() {
    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var uId: String

    private lateinit var journalRecyclerView: RecyclerView

    // implement clearing on logout
    private lateinit var journalArrayList: ArrayList<JournalData>
    // holds journal identifier keys
    private lateinit var entryKeyList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_table)

        // firebase authentication
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
        firebaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!).child("Journal")

        // getting the recyclerview by its id
        journalRecyclerView = findViewById(R.id.rvJournal)

        // creates a vertical layout Manager
        journalRecyclerView.layoutManager = LinearLayoutManager(this)

        // initialize lists
        journalArrayList = arrayListOf<JournalData>()
        entryKeyList = arrayListOf<String>()

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
}