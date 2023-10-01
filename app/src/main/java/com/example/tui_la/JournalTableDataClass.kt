package com.example.tui_la

import android.media.Image
import androidx.annotation.DrawableRes

data class JournalTableDataClass(
    var title: String,
    var time: String,
    var date: String,
    @DrawableRes var emotion: Int
)
