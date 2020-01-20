package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.presentation.MarvelLandingScreenActivity

interface MarvelLandingScreenContracts {

    interface presenter {
        fun init()
    }
    interface view {
        fun init()
        fun setAnimations()
    }
}