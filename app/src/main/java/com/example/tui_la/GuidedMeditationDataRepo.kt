package com.example.tui_la

/*data class recyclerViewItem(
    val name: String,
    val vid: Int
)*/
object GuidedMeditationDataRepo{
    val gmNames = mutableListOf<String>(
        "You Are Not Alone",
        "Basic Relaxation",
        "Difficult Emotions and Feeling Low",

    )
    val gmAud = mutableListOf<Int>(
        R.raw.you_are_not_alone_10min,
        R.raw.self_compassion_in_stressful_moments_10min,
        R.raw.ocean_breathing_10mins,

    )
    val gmImg = mutableListOf<Int>(
        R.drawable.pexels_marian_florinel_condruz_1188009,


    )
}
