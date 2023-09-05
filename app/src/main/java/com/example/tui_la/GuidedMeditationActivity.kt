package com.example.tui_la

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tui_la.GuidedMeditationAdapter
import com.example.tui_la.GuidedMeditationDataRepo
import com.example.tui_la.R
import com.example.tui_la.GuidedMeditationDataClass

class GuidedMeditationActivity : AppCompatActivity(), GuidedMeditationAdapter.RecyclerViewEvent{
    private val data = createData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_meditation_selection)

        val recyclerView: RecyclerView = findViewById(R.id.gm_recyclerview)
        recyclerView.adapter = GuidedMeditationAdapter(data,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(position: Int) {
        val gmName = data[position]
        Toast.makeText(this,gmName.name,Toast.LENGTH_SHORT).show()
    }

    private fun createData():ArrayList<GuidedMeditationDataClass>{
        val titles = GuidedMeditationDataRepo.gmNames
        val aud = GuidedMeditationDataRepo.gmAud
        val img = GuidedMeditationDataRepo.gmImg
        val gmData = ArrayList<GuidedMeditationDataClass>()

        for (i in 0..2){
            gmData.add(i,GuidedMeditationDataClass(titles[i],aud[i],img[i]))
        }

        return gmData
    }


}
