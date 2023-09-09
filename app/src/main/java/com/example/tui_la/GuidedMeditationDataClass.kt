package com.example.tui_la

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class GuidedMeditationDataClass(
    var name: String,
    @RawRes val aud: Int,
    @DrawableRes val img: Int,
    @RawRes val vid: Int
)