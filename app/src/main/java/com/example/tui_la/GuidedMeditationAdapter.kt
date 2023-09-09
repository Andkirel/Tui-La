package com.example.tui_la

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GuidedMeditationAdapter(
    private val data: List<GuidedMeditationDataClass>,
    private val listener: RecyclerViewEvent
    //private val onClick:(GuidedMeditationDataClass)->Unit
) : RecyclerView.Adapter<GuidedMeditationAdapter.ItemViewHolder>(){

    //Setup variables to hold the instance of the views defined in RecyclerView Item Layout
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val txtview: TextView = view.findViewById(R.id.gmtesttextview)
        //val btn: Button = view.findViewById(R.id.gm_recyclerview_btn)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_meditation_recyclerview_row,parent,false)
        return ItemViewHolder(inflatedView)
    }

    //Set values to views pulled from RecyclerView row
    //layout file based on position of RecyclerView
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val selection: GuidedMeditationDataClass = data[position]
        holder.txtview.text = selection.name
        holder.txtview.setOnClickListener(listener)
        //holder.itemView.setBackgroundResource(selection.img)
        holder.txtview.setBackgroundResource(selection.img)
        //holder.title.text = selection.name
        //holder.image.setImageResource(selection.img)
    }

    //Returns number of items in RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }

    interface RecyclerViewEvent : View.OnClickListener {
        fun onItemClick(position: Int)
    }

}