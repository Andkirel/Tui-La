package com.example.tui_la.com.example.tui_la

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class JournalAdapter (private val entryList : ArrayList<JournalEntryList>) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")

        return entryList.size
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class JournalViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var emotionImage : ShapeableImageView = itemView.findViewById(R.id.emotionImage)
        var timeHeading: TextView = itemView.findViewById(R.id.timeHeading)
        var dateHeading: TextView = itemView.findViewById(R.id.dateHeading)
        var titleHeading: TextView = itemView.findViewById(R.id.titleHeading)
    }
}