package com.example.pipexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerListeners()
    }

    private fun registerListeners() {
        img_thumbnail_first.setOnClickListener {
            startPlaybackActivity(R.raw.girl)
        }

        img_thumbnail_second.setOnClickListener {
            startPlaybackActivity(R.raw.guitar)
        }
    }

    private fun startPlaybackActivity(videoIndex: Int) {
        startActivity(
            Intent(this, PlaybackActivity::class.java).apply {
                putExtra(EXTRA_VIDEO_INDEX, videoIndex)
            }
        )
    }

    companion object {
        const val EXTRA_VIDEO_INDEX = "com.example.pipexample.EXTRA_VIDEO_INDEX"
    }
}
