package com.example.tui_la

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tui_la.JournalTableAdapter.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class JournalTableActivity : AppCompatActivity() {
    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference

    private lateinit var journalRecyclerView: RecyclerView
    private lateinit var journalArrayList: ArrayList<JournalData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_table)

        // firebase authentication
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
        firebaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.uid!!).child("Journal")
        //firebaseReference = Firebase.database.

        // getting the recyclerview by its id
        journalRecyclerView = findViewById(R.id.rvJournal)

        // creates a vertical layout Manager
        journalRecyclerView.layoutManager = LinearLayoutManager(this)

        // list of class JournalTable
        journalArrayList = arrayListOf<JournalData>()

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
                    }
                    // Setting the adapter to the recyclerview
                    val jAdapter = JournalTableAdapter(journalArrayList)
                    journalRecyclerView.adapter = jAdapter

                    jAdapter.setOnItemClickListener(object : JournalTableAdapter.onitemClickListener{
                        override fun onItemClick(position: Int) {
                            val journalWrite = Intent(this@JournalTableActivity, JournalWriteActivity::class.java)

                            journalWrite.putExtra("journalTitle", journalArrayList[position].title)
                            journalWrite.putExtra("journalEntry", journalArrayList[position].entry)
                            startActivity(journalWrite)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}