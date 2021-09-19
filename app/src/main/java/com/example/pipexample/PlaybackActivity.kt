package com.example.pipexample

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.util.Rational
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_playback.*


class PlaybackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playback)

        prepareSource(intent)
        registerListeners()
    }

    private fun registerListeners() {
        img_button_pip.setOnClickListener {
            enterPipMode()
        }
    }

    private fun enterPipMode() {
        val d: Display = windowManager.defaultDisplay
        val p = Point()
        d.getSize(p)
        val width: Int = p.x
        val height: Int = p.y
        val ratio = Rational(width, height)
        val pipBuilder = PictureInPictureParams.Builder()
        pipBuilder.setAspectRatio(ratio)
        hideUnnecessaryViews()
        this.enterPictureInPictureMode(pipBuilder.build())
    }

    private fun prepareSource(intent: Intent?) {
        val defaultIndex = -1
        val videoIndex = intent?.getIntExtra(MainActivity.EXTRA_VIDEO_INDEX, defaultIndex)
        val path = "android.resource://$packageName/$videoIndex"
        video_view.apply {
            setVideoURI(Uri.parse(path))
            start()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        video_view.apply {
            stopPlayback()
            releaseInstance()
            prepareSource(intent)
        }
    }

    private fun hideUnnecessaryViews() {
        img_button_pip.isVisible = false
    }

    private fun showViews() {
        img_button_pip.isVisible = true
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        when (isInPictureInPictureMode) {
            true -> hideUnnecessaryViews()
            false -> showViews()
        }
    }
}
