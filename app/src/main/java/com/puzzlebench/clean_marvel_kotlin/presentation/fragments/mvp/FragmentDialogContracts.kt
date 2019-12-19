package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import com.puzzlebench.cmk.domain.model.Character
import io.reactivex.Observable
import io.reactivex.Single

interface FragmentDialogContracts {
    interface Model {
        fun getSingleCharacterServiceUseCase(): Single<List<Character>>
    }

    interface View {
        fun showDialogFragment(characterFragment: CharacterFragmentDialog)
        fun showToastNoItem()
        fun showLoading(fragment: CharacterFragmentDialog)
        fun hideLoading(fragment: CharacterFragmentDialog)
    }

    interface Presenter {

        fun init(characterFragment: CharacterFragmentDialog)

    }
}