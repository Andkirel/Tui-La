package com.example.tui_la


enum class Icon(val value: Int, val image: Int){
    Afraid(1,R.drawable.afraid),
    Angry(2, R.drawable.angry),
    Excited(3, R.drawable.excited),
    Happy(4, R.drawable.happy),
    Loved(5, R.drawable.loved),
    Relaxed(6, R.drawable.relaxed),
    Sad(7, R.drawable.sad),
    Sleepy(8, R.drawable.sleepy),
    Stressed(9, R.drawable.stressed);

    object Emotions {
        private val emotionArray = intArrayOf(
            /*R.drawable.afraid,
            R.drawable.angry,
            R.drawable.excited,
            R.drawable.happy,
            R.drawable.loved,
            R.drawable.relaxed,
            R.drawable.sad,
            R.drawable.sleepy,
            R.drawable.stressed,*/
            Afraid.image,
            Angry.image,
            Excited.image,
            Happy.image,
            Loved.image,
            Relaxed.image,
            Sad.image,
            Sleepy.image,
            Stressed.image
        )

        var list: ArrayList<Icon>? = null
            get() {

                if (field != null)
                    return field

                field = ArrayList()
                for (i in emotionArray.indices) {

                    val imageId = emotionArray[i]
                }

                return field
            }
    }

 /*   fun getImage(): Int {
        return this.resourceId
    }
    fun getValue(): Int {
        return this.value
    }*/
}
