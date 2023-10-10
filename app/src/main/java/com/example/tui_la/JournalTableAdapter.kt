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
        var selection: JournalData = entryList[position]

        var title: String
        var time: String
        var date: String
        var sampleText: String
        var emotion: ImageView

        // sets the title text
        holder.title.text = selection.title
        // sets the time text
        holder.time.text = selection.time
        // sets the date text
        holder.date.text = selection.date
        // sets the preview of the journal entry
        holder.sampleText.text = selection.sampleText.substring(0,31).plus("...")
        // sets the emotion drawable
        holder.emotion.setImageResource(selection.emotion)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    class ViewHolder(entryView: View) :
        RecyclerView.ViewHolder(entryView)/*, View.OnClickListener*/ {
        var title: TextView = entryView.findViewById(R.id.tvTitle)
        var date: TextView = entryView.findViewById(R.id.tvDate)
        var time: TextView = entryView.findViewById(R.id.tvTime)
        var sampleText: TextView = entryView.findViewById(R.id.tvJournalSample)
        var emotion: ImageView = entryView.findViewById(R.id.ivEmotion)
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
