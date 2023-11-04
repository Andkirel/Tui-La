package com.example.tui_la

class EmotionMap(private val hashMap : HashMap<Int, /*Int*/ Icon> = HashMap()) {
    /*var hashMap : HashMap<Int, DrawableRes>
            = HashMap<Int, DrawableRes> ()*/

    // initializer block
    init {
        /*hashMap[1] = R.drawable.afraid
        hashMap[2] = R.drawable.angry
        hashMap[3] = R.drawable.excited
        hashMap[4] = R.drawable.happy
        hashMap[5] = R.drawable.loved
        hashMap[6] = R.drawable.relaxed
        hashMap[7] = R.drawable.sad
        hashMap[8] = R.drawable.sleepy
        hashMap[9] = R.drawable.stressed*/
        hashMap[1] = Icon.Afraid
        hashMap[2] = Icon.Angry
        hashMap[3] = Icon.Excited
        hashMap[4] = Icon.Happy
        hashMap[5] = Icon.Loved
        hashMap[6] = Icon.Relaxed
        hashMap[7] = Icon.Sad
        hashMap[8] = Icon.Sleepy
        hashMap[9] = Icon.Stressed

    }
    fun getDrawableValue(key: Int) : /*Int*/Icon {
        return hashMap.getValue(key)
    }
    fun getDrawableKey(value: /*Int*/Icon) : Int {
        val reversedMap = hashMap.entries.associate{(k,v) -> v to k}
        return reversedMap.getValue(value)
    }
}