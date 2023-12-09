package com.example.tui_la

data class JournalData(
    val title: String? = null,
    val time: String? = null,
    val date: String? = null,
    val entry: String? = null,
    val emotion: String? = null
) {
    constructor() : this("","","","","")
}