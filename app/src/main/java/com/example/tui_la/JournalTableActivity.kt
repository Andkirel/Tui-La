package com.example.tui_la

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
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

@UnstableApi class JournalTableActivity : AppCompatActivity() {

    private val email = "ed'stestuser@gmail.com"
    private val password = "abcdef"

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var uid: String

    private lateinit var journalRecyclerView: RecyclerView

    private lateinit var backButton: Button

    // implement clearing on logout
    private lateinit var journalArrayList: ArrayList<JournalData>
    // holds journal identifier keys
    private lateinit var entryKeyList: ArrayList<String>

    private var reverse: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_table)

        //uid = intent.getStringExtra("UID") ?: return

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email,password)
        firebaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Journal")

        // getting the recyclerview by its id
        journalRecyclerView = findViewById(R.id.rvJournal)

        // back button to home activity
        backButton = findViewById(R.id.button_records_backButton)

        // creates a vertical layout Manager
        journalRecyclerView.layoutManager = LinearLayoutManager(this)

        // initialize lists
        journalArrayList = arrayListOf<JournalData>()
        entryKeyList = arrayListOf<String>()

        backButton.setOnClickListener {
            backToHome()
        }

        // get data; pull from firebase
        getUserData()
    }

    private fun getUserData() {
        firebaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if (journalArrayList.isEmpty()) {
                        for (key in snapshot.children) {
                            val userData = key.getValue(JournalData::class.java)
                            journalArrayList.add(userData!!)
                            entryKeyList.add(key.key.toString())
                        }
                    }
                    // Setting the adapter to the recyclerview
                    if (reverse)
                        reverseList()

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
                                    Toast.makeText(this@JournalTableActivity,"Entry deleted",Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{err ->
                                    Toast.makeText(this@JournalTableActivity, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                                }
                            journalArrayList.removeAt(position)
                            entryKeyList.removeAt(position)
                            jAdapter.notifyItemRemoved(position)
                            jAdapter.notifyItemRangeChanged(position, journalArrayList.size)
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
        onBackPressed();
    }

    private fun reverseList(){
        journalArrayList.reverse()
        entryKeyList.reverse()
    }

}