package com.example.tui_la

import androidx.annotation.RawRes

data class GuidedMeditationDataClass(
    val name: String,
    @RawRes val aud: Int,
    val img: Int
)