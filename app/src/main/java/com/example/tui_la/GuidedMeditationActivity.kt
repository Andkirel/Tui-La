package com.example.tui_la

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuidedMeditationActivity : AppCompatActivity(), GuidedMeditationAdapter.RecyclerViewEvent {
    private val data = createData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_selection)

        val recyclerView: RecyclerView = findViewById(R.id.gm_recyclerview)
        recyclerView.adapter = GuidedMeditationAdapter(createData(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun createData(): List<GuidedMeditationDataClass>{
        val names = GuidedMeditationDataRepo.gmNames
        val vids = GuidedMeditationDataRepo.gmAud
        var img = GuidedMeditationDataRepo.gmImg
        val selectionData = ArrayList<GuidedMeditationDataClass>()
        for(i in 0..2){
            selectionData.add(
                GuidedMeditationDataClass(
                    name = names[i],
                    aud = vids[i],
                    image = img[i]
                )
            )
        }
        return selectionData
    }

    override fun onItemClick(position: Int) {
        val pickedrow = data[position]

        //Change from Toast to open to new fragment or whatever upon clicking
        //Toast is here for testing
        Toast.makeText(this,pickedrow.name,Toast.LENGTH_SHORT).show()
    }
}

