package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FragmentDialogPresenter(val view: FragmentDialogView, val model: FragmentDialogModel): FragmentDialogContracts.Presenter {

    private lateinit var disposable: Disposable
    override fun init(characterFragment: CharacterFragmentDialog) {
        requestGetSingleCharacter(characterFragment)
    }

    private fun requestGetSingleCharacter(fragment: CharacterFragmentDialog){
        disposable = model.getSingleCharacterServiceUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ character ->
                    if (character.isEmpty()){
                        view.showToastNoItem()
                    } else {
                        view.showDialogFragment(fragment, character.last())
                    }
                    view.hideLoading(fragment)
                }, { e ->
                    view.hideLoading(fragment)
                    view.showToastNetworkError(e.message.toString())
                })
    }

    fun dispose(){
        disposable.dispose()
    }
}