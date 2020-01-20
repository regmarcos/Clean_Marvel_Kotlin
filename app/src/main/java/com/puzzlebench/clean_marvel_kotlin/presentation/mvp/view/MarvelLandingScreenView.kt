package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import android.os.Handler
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat.startActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.DURATION
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity.Companion.getStartIntent
import com.puzzlebench.clean_marvel_kotlin.presentation.MarvelLandingScreenActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.ONE_FLOAT
import com.puzzlebench.clean_marvel_kotlin.presentation.ZERO_FLOAT
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.MarvelLandingScreenContracts
import kotlinx.android.synthetic.main.activity_marvel_landing_screen.logo_marvel
import kotlinx.android.synthetic.main.activity_marvel_landing_screen.progress_bar_landing_screen
import java.lang.ref.WeakReference

class MarvelLandingScreenView(activity: MarvelLandingScreenActivity) : MarvelLandingScreenContracts.view {

    private val activityRef = WeakReference(activity)
    override fun init() {
        setAnimations()
    }

    override fun setAnimations() {
        activityRef.get().let {
            it?.progress_bar_landing_screen?.isIndeterminate = true
            val fadeIn = AlphaAnimation(ZERO_FLOAT, ONE_FLOAT)
            fadeIn.duration = DURATION
            it?.logo_marvel?.animation = fadeIn
            it?.logo_marvel?.startAnimation(fadeIn)
            Handler().postDelayed({
                if (it != null) {
                    startActivity(it, getStartIntent(it), null)
                }
            }, DURATION)
        }
    }
}