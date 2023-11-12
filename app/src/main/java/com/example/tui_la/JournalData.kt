package com.example.tui_la

data class JournalData(
    val title: String? = null,
    val time: String? = null,
    val date: String? = null,
    val entry: String? = null,
    val emotion: Int? = null
) {
    constructor() : this("","","","",0)
}