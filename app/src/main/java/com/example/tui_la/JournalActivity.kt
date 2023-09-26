package com.example.tui_la

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tui_la.com.example.tui_la.JournalFragment

class JournalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_contents)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JournalFragment.newInstance())
                .commitNow()
        }

        // getting the recyclerview by its id
        var recyclerview = findViewById<RecyclerView>(R.id.rvJournal)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class JournalTableDataClass
        val data = ArrayList<JournalTableDataClass>()

        // pass the entryList to our Adapter
        val adapter = JournalTableAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }



/*    fun backButtonClick(view: View) {

        var userText = findViewById<EditText>(R.id.journalText)

        private lateinint var database : DatabaseReference
        database = Firebase.database.reference

    }

    fun writtenJournalChosen(view: View) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<Post>()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
    }*/
}