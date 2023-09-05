package com.example.tui_la

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuidedMeditationAdapter(private val data: List<GuidedMeditationDataClass>, private val listener: RecyclerViewEvent) : RecyclerView.Adapter<GuidedMeditationAdapter.ItemViewHolder>() {

    //Setup variables to hold the instance of the views defined in RecyclerView Item Layout
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val title: TextView = view.findViewById(R.id.recyclerview_row_gm_title)
        val image: ImageButton = view.findViewById(R.id.gm_imagebutton)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    //Inflating layout (give each entry/row its look)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_meditation_recyclerview_row,parent,false)
        return ItemViewHolder(inflatedView)
    }

    //Set values to views pulled from RecyclerView row
    //layout file based on position of RecyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val selection: GuidedMeditationDataClass = data[position]

        holder.title.text = selection.name
        holder.image.setImageResource(selection.image)
    }

    //Returns number of items in RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }

    interface RecyclerViewEvent{
        fun onItemClick(position: Int)
    }
}