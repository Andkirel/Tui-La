package com.example.tui_la

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalTableAdapter(private var entryList : MutableList<JournalData>,
                          /*private var listener: RecyclerViewEvent*/
    ) : RecyclerView.Adapter<JournalTableAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalTableAdapter.ViewHolder {
        var inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_journal_table_items, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selection: JournalData = entryList[position]

 /*     var title: String
        var time: String
        var date: String
        var entry: String
        var emotion: ImageView*/

        holder.title.text = selection.title
        holder.time.text = selection.time
        holder.date.text = selection.date
        holder.entry.text = selection.entry/*!!.substring(0,31).plus("...")*/
        holder.emotion.setImageResource(selection.emotion)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    class ViewHolder(entryView: View) :
        RecyclerView.ViewHolder(entryView)/*, View.OnClickListener*/ {
        val title: TextView = entryView.findViewById(R.id.tvTitle)
        val date: TextView = entryView.findViewById(R.id.tvDate)
        val time: TextView = entryView.findViewById(R.id.tvTime)
        val entry: TextView = entryView.findViewById(R.id.tvEntry)
        val emotion: ImageView = entryView.findViewById(R.id.ivEmotion)
    }
}
    /*    init {
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
        }*/
