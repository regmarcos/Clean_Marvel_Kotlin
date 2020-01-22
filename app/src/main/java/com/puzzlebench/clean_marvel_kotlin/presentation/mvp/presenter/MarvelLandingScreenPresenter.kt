package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter

import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.contracts.MarvelLandingScreenContracts

class MarvelLandingScreenPresenter(val view: MarvelLandingScreenContracts.View) : MarvelLandingScreenContracts.Presenter {
    override fun init() {
        view.init()
    }
}