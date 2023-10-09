package com.example.tui_la

import androidx.annotation.DrawableRes
import com.google.firebase.database.DatabaseReference

data class JournalTable(
    var title: String? = null,
    var time: String? = null,
    var date: String? = null,
    var sampleText: String,
    @DrawableRes var emotion: Int
)

