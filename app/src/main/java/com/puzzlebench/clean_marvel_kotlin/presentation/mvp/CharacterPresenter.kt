package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetCharacterRepositoryUseCase
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter constructor(view: CharacterView,
                         private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                         private val getCharacterRepositoryUseCase: GetCharacterRepositoryUseCase,
                         private val saveCharacterRepositoryUseCase: SaveCharacterRepositoryUseCase,
                         val subscriptions: CompositeDisposable) : Presenter<CharacterView>(view) {

    lateinit var characters: List<Character>
    fun init() {
        view.init()
        characters = getCharacterRepositoryUseCase.invoke()
        if (characters.isEmpty()) {
            requestGetCharacters()
        } else {
            view.hideLoading()
            view.showCharacters(characters)
        }
    }

    private fun requestGetCharacters() {
        val subscription = getCharacterServiceUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
            if (characters.isEmpty()) {
                view.showToastNoItemToShow()
            } else {
                saveCharacterRepositoryUseCase.invoke(characters)
                view.showCharacters(characters)
            }
            view.hideLoading()
            view.showFAB()
                }, { e ->
            view.hideLoading()
            view.showFAB()
            view.showToastNetworkError(e.message.toString())
        })
        subscriptions.add(subscription)
    }

    fun onClickRefreshFAB() {
        characters = emptyList()
        view.hideFAB()
        view.showCharacters(characters)
        view.showLoading()
        requestGetCharacters()
    }

    fun onClickDatabaseFAB() {
        view.hideFAB()
        characters = emptyList()
        view.showCharacters(characters)
        view.showLoading()
        characters = getCharacterRepositoryUseCase.invoke()
        view.showCharacters(characters)
        view.showFAB()
        view.hideLoading()
    }

    fun onClickClearFAB() {
        characters = emptyList()
        view.showCharacters(characters)
    }

}
