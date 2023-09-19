package com.example.tui_la

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class JournalTableAdapter(private var entryList : MutableList<JournalTableDataClass>,
                          private var listener: RecyclerViewEvent
    ) : RecyclerView.Adapter<JournalTableAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalTableAdapter.ViewHolder {
        var inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_journal_contents_items,parent,false)

        return ViewHolder(inflatedView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var selection: JournalTableDataClass = (entryList[position])
//      to be continued
    }
    override fun getItemCount() = entryList.size
    inner class ViewHolder(entryView : View) : RecyclerView.ViewHolder(entryView), View.OnClickListener{
        var name: TextView = entryView.findViewById(R.id.tvName)
        init {
            entryView.setOnClickListener{
                if (bindingAdapterPosition >= 0){
                    listener.onItemClick(bindingAdapterPosition)
                }
            }
        }
        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
//        var emotionImage : ShapeableImageView = itemView.findViewById(R.id.emotionImage)
//        var timeHeading: TextView = itemView.findViewById(R.id.timeHeading)
//        var dateHeading: TextView = itemView.findViewById(R.id.dateHeading)
//        var titleHeading: TextView = itemView.findViewById(R.id.titleHeading)
    }


}