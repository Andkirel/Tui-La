package com.example.tui_la

import androidx.annotation.DrawableRes

data class GuidedMeditationData (
    var name: String,
    val trackId: Int,
    @DrawableRes val img: Int
)

object GuidedMeditationDataRepo {
    val gmNames = mutableListOf(
        "Attracting Abundance into Our Lives",
        "Releasing Control through Breath",
        "Making Space Where You Are",
        "Making Space with the Full Moon",
        "Slow Down - A Meditation for Stress and Overwhelm",
        "Guided Healing Meditation"
    )
    val soundcloudTrackID = mutableListOf(
        314179591,
        1016402329,
        1174448758,
        1074950746,
        683387639,
        796953958
    )
    val gmImgs = mutableListOf(
        R.drawable.excited
    )
}