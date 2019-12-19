package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import android.annotation.SuppressLint
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FragmentDialogPresenter(val view: FragmentDialogView, val model: FragmentDialogModel): FragmentDialogContracts.Presenter {

    override fun init(fragment: CharacterFragmentDialog) {
        //view.showLoading(fragment)
        requestGetSingleCharacter(fragment)
    }
    @SuppressLint("CheckResult")
    private fun requestGetSingleCharacter(fragment: CharacterFragmentDialog){
        model.getSingleCharacterServiceUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ character ->
                    if (character.isEmpty()){
                        view.showToastNoItem()
                    }
                    else {
                        view.hideLoading(fragment)
                        view.showDialogFragment(fragment)
                    }
                })
    }
}