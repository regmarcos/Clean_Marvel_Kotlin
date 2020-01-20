package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter

import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.MarvelLandingScreenContracts
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.MarvelLandingScreenView

class MarvelLandingScreenPresenter(val view: MarvelLandingScreenView): MarvelLandingScreenContracts.presenter {
    override fun init() {
        view.init()
    }
}