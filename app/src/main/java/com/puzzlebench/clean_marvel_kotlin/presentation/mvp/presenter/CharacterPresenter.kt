package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter

import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.contracts.CharacterContracts
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetCharacterRepositoryUseCase
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter(private val view: CharacterContracts.View,
                         private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                         private val getCharacterRepositoryUseCase: GetCharacterRepositoryUseCase,
                         private val saveCharacterRepositoryUseCase: SaveCharacterRepositoryUseCase,
                         private val subscriptions: CompositeDisposable
) : CharacterContracts.Presenter {

    lateinit var characters: List<Character>
    override fun init() {
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

    override fun onClickRefreshFAB() {
        view.hideFAB()
        view.showCharacters(emptyList())
        view.showLoading()
        requestGetCharacters()
    }

    override fun onClickDatabaseFAB() {
        view.hideFAB()
        view.showCharacters(emptyList())
        view.showLoading()
        view.showCharacters(getCharacterRepositoryUseCase.invoke())
        view.showFAB()
        view.hideLoading()
    }

    override fun onClickClearFAB() {
        view.showCharacters(emptyList())
    }
}
