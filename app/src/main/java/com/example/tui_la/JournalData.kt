package com.example.tui_la

import androidx.annotation.DrawableRes

data class JournalData(
    var title: String? = null,
    var time: String? = null,
    var date: String? = null,
    var entry: String,
    @DrawableRes var emotion: Int
)

