package com.example.tui_la

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem

data class GuidedMeditationDataClass(
    val name: String,
    val mediaItem: MediaItem,
    @RawRes val aud: Int,
    @DrawableRes val image: Int
)