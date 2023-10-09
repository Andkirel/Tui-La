package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tui_la.com.example.tui_la.JournalFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JournalActivity : AppCompatActivity() {

    private lateinit var database:DatabaseReference
    private lateinit var journalRecyclerView: RecyclerView
    // List of class JournalTable
    private lateinit var journalArrayList: ArrayList<JournalTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_journal_contents)

        // getting the recyclerview by its id
        journalRecyclerView = findViewById(R.id.rvJournal)

        // creates a vertical layout Manager
        journalRecyclerView.layoutManager = LinearLayoutManager(this)

        // list of class JournalTable
        journalArrayList = arrayListOf<JournalTable>()

        // get data; pull from firebase
        getUserData()

/*
        // sample info
        for (i in 1..10) {
            journalArrayList.add(JournalTable("Test entry title " + i, "time " + i,"date " + i,"sample" + i, R.drawable.happy))
        }
*/
    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance().getReference("Users")

        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(JournalTable::class.java)
                        journalArrayList.add(user!!)

                    }
                    // Setting the adapter to the recyclerview
                    journalRecyclerView.adapter = JournalTableAdapter(journalArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}