package com.example.tui_la

/*data class recyclerViewItem(
    val name: String,
    val vid: Int
)*/
object GuidedMeditationDataRepo{
    val gmNames = mutableListOf<String>(
        "You Are Not Alone",
        "Basic Relaxation",
        "Ocean Breathing",
        "Allowing Uncertainty",
        "Quick Calm"
    )
    val gmAud = mutableListOf<Int>(
        R.raw.you_are_not_alone_10min,
        R.raw.basic_relaxation_10mins,
        R.raw.ocean_breathing_10mins,
        R.raw.allowing_uncertainty_10mins,
        R.raw.quick_calm_10mins
    )
    val gmImg = mutableListOf<Int>(
        R.drawable.pexels_trace_hudson_2605867,
        R.drawable.pexels_maher_abiad_330512,
        R.drawable.pexels_marian_florinel_condruz_1188009,
        R.drawable.pexels_darwis_alwan_1735675,
        R.drawable.pexels_mo_eid_11592804
    )
}
