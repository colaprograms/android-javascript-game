package com.skeletonplanet.tapshoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

const val EXCEPTION_TEXT = "com.skeletonplanet.tapshoe.EXCEPTION_TEXT"

class exceptionactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exceptionactivity)
        val except = intent.getStringExtra(EXCEPTION_TEXT)
        findViewById<TextView>(R.id.exception).apply {
            text = except
        }
    }
}
