package com.example.tui_la

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuidedMeditationAdapter(
    private val data: List<GuidedMeditationData>,
    private val listener: RecyclerViewEvent,
) : RecyclerView.Adapter<GuidedMeditationAdapter.ViewHolder>(){

    //Setup variables to hold the instance of the views defined in fragment_meditation_recyclerview_row.xml
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val textView: TextView = view.findViewById(R.id.gmrowtextview)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    //Inflating layout (give each entry/row its look)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_meditation_recyclerview_row,parent,false)
        return ViewHolder(inflatedView)
    }

    //Set values to views pulled from RecyclerView row
    //layout file based on position of RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selection: GuidedMeditationData = data[position]
        holder.textView.text = selection.trackName
        holder.textView.setBackgroundResource(selection.gmImages)
        holder.textView.setOnClickListener{listener}
    }

    //Returns number of items in RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }
/*interface RecyclerViewEvent: View.OnClickListener {*/
    interface RecyclerViewEvent {
        fun onItemClick(position: Int)
    }

}