package com.example.tui_la

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.firebase.annotations.concurrent.Background
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Application.Execution.Thread.Frame

class EmotionImage : FrameLayout {

    private lateinit var rootLayout: FrameLayout
    private lateinit var background: Icon
    constructor(context: Context) : super (context) {
        initEmotionImage(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super (context, attrs) {
        initEmotionImage(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initEmotionImage(context)
    }

    private fun initEmotionImage(context: Context) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        LayoutInflater.from(context).inflate(R.layout.layout_emotion_image, this, true)
        rootLayout = findViewById(R.id.emotion_root_layout)
    }
}