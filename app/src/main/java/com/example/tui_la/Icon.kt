package com.example.tui_la

import androidx.annotation.DrawableRes

enum class Icon(private val value: Int, @DrawableRes val resourceId: Int){
    Afraid(1,R.drawable.afraid),
    Angry(2, R.drawable.angry),
    Excited(3, R.drawable.excited),
    Happy(4, R.drawable.happy),
    Loved(5, R.drawable.loved),
    Relaxed(6, R.drawable.relaxed),
    Sad(7, R.drawable.sad),
    Sleepy(8, R.drawable.sleepy),
    Stressed(9, R.drawable.stressed);

    fun getImage(): Int {
        return this.resourceId
    }
    fun getValue(): Int {
        return this.value
    }
}
