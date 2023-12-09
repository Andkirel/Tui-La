package com.example.tui_la

data class JournalHashMap(val name: String, val image: Int)
object HMData {
    private val emMap : HashMap<String, Int> = hashMapOf(
        "Afraid" to R.drawable.afraid,
        "Angry" to R.drawable.angry,
        "Excited" to R.drawable.excited,
        "Happy" to R.drawable.happy,
        "Loved" to R.drawable.loved,
        "Relaxed" to R.drawable.relaxed,
        "Sad" to R.drawable.sad,
        "Sleepy" to R.drawable.sleepy,
        "Stressed" to R.drawable.stressed,
    )
    var list: ArrayList<JournalHashMap>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()
            emMap.forEach{ (key,value) ->
                val emotion = JournalHashMap(key, value)
                field!!.add(emotion)
            }
            return field
        }
    fun getValue(key: String) = emMap[key]!!

    fun getKey(value: Int): String {
        val reverseMap = emMap.entries.associate {(k,v)-> v to k}

        return reverseMap[value]!!
    }
}