package com.example.tui_la.com.example.tui_la

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tui_la.R
import com.google.android.material.imageview.ShapeableImageView
import com.ezatpanah.recyclerView.databinding.ItemRowBinding


class JournalAdapter (val entryList : MutableList<JournalEntry>)
    : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    private lateinit var binding: ItemRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRowBinding.inflate(inflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalAdapter.ViewHolder, position: Int) {
        holder.bind(entryList[position])
    }

    override fun getItemCount() = entryList.size


    inner class ViewHolder(entryView : ItemRowBinding) : RecyclerView.ViewHolder(entryView.root){
//        fun bind(item: JournalEntry){
//            binding.apply {
//                tvId.text
//            }
//        }
        var name: TextView = entryView.findViewById(R.id.tvName)


//        var emotionImage : ShapeableImageView = itemView.findViewById(R.id.emotionImage)
//        var timeHeading: TextView = itemView.findViewById(R.id.timeHeading)
//        var dateHeading: TextView = itemView.findViewById(R.id.dateHeading)
//        var titleHeading: TextView = itemView.findViewById(R.id.titleHeading)
    }


}