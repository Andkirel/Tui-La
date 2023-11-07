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
    val gmGoogleAudIds = mutableListOf(
        "1LkHp3yXtwVxVFUaqDPK41MZE-JE8cVjc",
        "12CflTXe6_8KKRYOYq0Ssx41lpQUsJXip",
        "1vvYq0ie0QmNvgHpLeQMNYyZUNGikjeyf",
        "1uW0z7nqH6Yn0ADf9FT42kGJdP1Y6X_5P",
        "1vdYbTCZ57r-jsX9MoAzlOnMJJ7n3UcYU",
        "1KFb5qLgF0DcDycDGKkWQBQmFgduRP0XR",
        "1sdE8as0owVMP7T8vsXL4ZDJg_zBJhVbY"
    )
    val gmGoogleVidIds = mutableListOf(
        "1aFoBLtg2I8vJRQRnNzVn4WbSMgZUZWw1",
        "1haGwmAqQ5o8SECfCfJFIwG-uKMActRal",
        "1vdd9uOXsdlCbtFRUxFELa4i06I3bIZTv",
        "1v7n378ZnsAdtiWOMpZ2YQC4CFnbrGj-r",
        "1RMdKtJUQoYA4t1KfycAuq24QizkKemx7",
        "1SZgUNAiUKIArJE_J3P9taC0ONK3rGWAl",
        "1K-zIIxEb0E7TcsKdZ2AEEuoo-CxWO7A1"
    )
}