package com.example.tui_la

import androidx.annotation.DrawableRes

data class JournalData(
    val title: String? = null,
    val time: String? = null,
    val date: String? = null,
    val entry: String? = null,
    @DrawableRes val emotion: Int
)

