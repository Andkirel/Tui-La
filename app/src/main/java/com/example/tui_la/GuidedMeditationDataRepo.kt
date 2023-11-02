package com.example.tui_la

import androidx.annotation.DrawableRes

data class GuidedMeditationData (
    var trackName: String,
    val trackId: Int,
    @DrawableRes val gmImages: Int
)

object GuidedMeditationDataRepo {
    val gmNames = mutableListOf(
        "Appreciative Joy",
        "Guided Sitting Meditation",
        "Compassion Meditation",
        "Meditation for Difficult Emotions",
        "Working with Difficult Opposing Council",
        "Breath Control",
        "Seedling"
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
        R.drawable.gm_still_morning_river_sunbeams,
        R.drawable.gm_still_waterfalls_over_pond_facing_right,
        R.drawable.gm_still_waves_from_above,
        R.drawable.gm_still_slow_waves_closeup,
        R.drawable.gm_still_large_moon_on_lake,
        R.drawable.gm_still_steaming_teacup_with_sun,
        R.drawable.gm_still_windy_meadow
    )
}