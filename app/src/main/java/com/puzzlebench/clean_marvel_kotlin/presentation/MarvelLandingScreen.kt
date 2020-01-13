package com.puzzlebench.clean_marvel_kotlin.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import com.puzzlebench.clean_marvel_kotlin.R
import kotlinx.android.synthetic.main.activity_marvel_landing_screen.logo_marvel
import kotlinx.android.synthetic.main.activity_marvel_landing_screen.progress_bar_landing_screen

class MarvelLandingScreen : AppCompatActivity() {
    private val duration = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel_landing_screen)
        setAnimations()
    }

    private fun setAnimations() {
        progress_bar_landing_screen.isIndeterminate = true
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = duration.toLong()
        logo_marvel.animation = fadeIn
        logo_marvel.startAnimation(fadeIn)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, duration.toLong())
    }
}