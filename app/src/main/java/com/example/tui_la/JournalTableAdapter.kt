package com.example.tui_la

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalTableAdapter(private var entryList : MutableList<JournalData>
    ) : RecyclerView.Adapter<JournalTableAdapter.ViewHolder>() {

    private lateinit var jListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        jListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_journal_table_items, parent, false)

        return ViewHolder(inflatedView, jListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selection: JournalData = entryList[position]

        holder.title.text = selection.title
        holder.time.text = selection.time
        holder.date.text = selection.date
        holder.entry.text = selection.entry/*!!.substring(0,31).plus("...")*/
        holder.emotion.setImageResource(selection.emotion!!)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    class ViewHolder(entryView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder(entryView){
        val title: TextView = entryView.findViewById(R.id.tvTitle)
        val date: TextView = entryView.findViewById(R.id.tvDate)
        val time: TextView = entryView.findViewById(R.id.tvTime)
        val entry: TextView = entryView.findViewById(R.id.tvEntry)
        val emotion: ImageView = entryView.findViewById(R.id.ivEmotion)

        init {
            entryView.setOnClickListener{
                clickListener.onItemClick(absoluteAdapterPosition)
            }
        }
    }
}

