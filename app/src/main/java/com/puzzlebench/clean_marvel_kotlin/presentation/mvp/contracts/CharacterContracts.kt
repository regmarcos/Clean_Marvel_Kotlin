package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.contracts

import com.puzzlebench.cmk.domain.model.Character

interface CharacterContracts {

    interface View {
        fun init()
        fun showToastNoItemToShow()
        fun showToastNetworkError(error: String)
        fun hideLoading()
        fun showCharacters(characters: List<Character>)
        fun showLoading()
        fun showFAB()
        fun hideFAB()
    }

    interface Presenter {
        fun init()
        fun onClickRefreshFAB()
        fun onClickDatabaseFAB()
        fun onClickClearFAB()
    }
}