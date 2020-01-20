package com.puzzlebench.clean_marvel_kotlin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.MarvelLandingScreenPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.MarvelLandingScreenView

class MarvelLandingScreenActivity : AppCompatActivity() {
    private val presenter = MarvelLandingScreenPresenter(MarvelLandingScreenView(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvel_landing_screen)
        presenter.init()
    }
}