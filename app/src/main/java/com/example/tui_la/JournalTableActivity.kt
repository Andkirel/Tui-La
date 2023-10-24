package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                if (snapshot.exists()) {
                    val userData = snapshot.getValue(JournalData::class.java)

                    journalArrayList.add(userData!!)

                    // Setting the adapter to the recyclerview
                    journalRecyclerView.adapter = JournalTableAdapter(journalArrayList)

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

 /*       firebaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children) {
                        val userData = userSnapshot.getValue(JournalData::class.java)
                        journalArrayList.add(userData!!)
                    }
                    //Setting the adapter to the recyclerview
                    journalRecyclerView.adapter = JournalTableAdapter(journalArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
/*        val entryListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val userData = snapshot.getValue(JournalData::class.java)
                //if (snapshot.exists()){
                    //for (userSnapshot in snapshot.children) {
                        //val userData = userSnapshot.getValue(JournalData::class.java)
                        //journalArrayList.add(userData!!)
                    //}

                //}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }*/
        //firebaseReference.addValueEventListener(entryListener)

        //Setting the adapter to the recyclerview
        //journalRecyclerView.adapter = JournalTableAdapter(journalArrayList)



/*
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(JournalData::class.java)
                        journalArrayList.add(user!!)

                    }
                    // Setting the adapter to the recyclerview
                    journalRecyclerView.adapter = JournalTableAdapter(journalArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
    }
}