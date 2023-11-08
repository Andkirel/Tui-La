package com.example.tui_la

import android.content.Context
import android.graphics.drawable.Icon
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.Int as Int

class JournalSpinnerAdapter(private var emotionList : MutableList<Emotion>, context: Context,
    ) : ArrayAdapter<Emotion>(context, 0, emotionList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val emotion = getItem(position)

        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.layout_journal_spinner_items,parent,false)
        view.findViewById<ImageView>(R.id.spinnerImage).setImageResource(emotion!!.image)
        //view.findViewById<ImageView>(R.id.spinnerImage).background.equals(R.color.non)


        //android:background="@android:color/transparent"

        return view
    }

}