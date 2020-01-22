package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.contracts

interface MarvelLandingScreenContracts {

    interface Presenter {
        fun init()
    }

    interface View {
        fun init()
        fun setAnimations()
    }
}