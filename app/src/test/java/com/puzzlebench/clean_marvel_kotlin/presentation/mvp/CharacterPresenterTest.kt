package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.repository.CharacterRepository
import com.puzzlebench.cmk.domain.service.CharacterServices
import com.puzzlebench.cmk.domain.usecase.GetCharacterRepositoryUseCase
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

//TODO fix on second iteration
// error: However, there was exactly 1 interaction with this mock:
class CharacterPresenterTest {

    private var view = mock(CharacterView::class.java)
    private var characterServiceImp = mock(CharacterServices::class.java)
    private var characterRepository = mock(CharacterRepository::class.java)

    private lateinit var characterPresenter: CharacterPresenter
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var getCharacterRepositoryUseCase: GetCharacterRepositoryUseCase
    private lateinit var saveCharacterRepositoryUseCase: SaveCharacterRepositoryUseCase

    companion object{
        const val NETWORK_ERROR = "NET ERROR"
    }

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        getCharacterRepositoryUseCase = GetCharacterRepositoryUseCase(characterRepository)
        saveCharacterRepositoryUseCase = SaveCharacterRepositoryUseCase(characterRepository)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view,
                getCharacterServiceUseCase,
                getCharacterRepositoryUseCase,
                saveCharacterRepositoryUseCase,
                subscriptions)
    }

    @Test
    fun init() {
        val itemsCharacters = listOf(1..5).map {
            mock(Character::class.java)
        }
        val observable = Single.just(itemsCharacters)
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        `when`(getCharacterRepositoryUseCase.invoke()).thenReturn(emptyList())
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(characterRepository).getAll()
        verify(view).hideLoading()
        verify(view).showFAB()
        verify(view).showCharacters(itemsCharacters)
    }

    @Test
    fun reposeWithError() {
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Single.error(Exception(NETWORK_ERROR)))
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoading()
        verify(view).showFAB()
        verify(view).showToastNetworkError(NETWORK_ERROR)
    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharacters = listOf(1..5).map {
            mock(Character::class.java)
        }
        val observable = Single.just(itemsCharacters)
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoading()
        verify(view).showFAB()
        verify(view).showCharacters(itemsCharacters)
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharacters = emptyList<Character>()
        val observable = Single.just(itemsCharacters)
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
    }

    @Test
    fun refreshFABReposeWithNetworkError() {
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Single.error(Exception(NETWORK_ERROR)))
        val itemsCharacters = emptyList<Character>()
        characterPresenter.onClickRefreshFAB()
        verify(view).showLoading()
        verify(view).hideFAB()
        verify(view).showCharacters(itemsCharacters)
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoading()
        verify(view).showToastNetworkError(NETWORK_ERROR)
    }

    @Test
    fun refreshFABResponseWithNoItemToShow() {
        val itemsCharacters = emptyList<Character>()
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Single.just(itemsCharacters))
        characterPresenter.onClickRefreshFAB()
        verify(view).hideFAB()
        verify(view).showCharacters(emptyList())
        verify(view).showLoading()
        verify(characterServiceImp).getCharacters()
        verify(view).showToastNoItemToShow()
        verify(view).hideLoading()
        verify(view).showFAB()
    }

    @Test
    fun refreshFABResponseWithItem() {
        val itemCharacters = listOf(1..5).map{
            mock(Character::class.java)
        }
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Single.just(itemCharacters))
        characterPresenter.onClickRefreshFAB()
        verify(view).hideFAB()
        verify(view).showCharacters(emptyList())
        verify(view).showLoading()
        verify(characterServiceImp).getCharacters()
        verify(characterRepository).save(itemCharacters)
        verify(view).showCharacters(itemCharacters)
        verify(view).hideLoading()
        verify(view).showFAB()
    }

    @Test
    fun databaseFABResponseWithNoItem() {
        `when`(getCharacterRepositoryUseCase.invoke()).thenReturn(emptyList())
        characterPresenter.onClickDatabaseFAB()
        verify(view).hideFAB()
        verify(view, times(2)).showCharacters(emptyList())
        verify(view).showLoading()
        verify(view).showFAB()
        verify(view).hideLoading()
    }

    @Test
    fun databaseFABResponseWithItem() {
        val itemCharacters = listOf(1..5).map {
            mock(Character::class.java)
        }
        `when`(getCharacterRepositoryUseCase.invoke()).thenReturn(itemCharacters)
        characterPresenter.onClickDatabaseFAB()
        verify(view).hideFAB()
        verify(view).showCharacters(emptyList())
        verify(view).showLoading()
        verify(view).showCharacters(itemCharacters)
        verify(view).showFAB()
        verify(view).hideLoading()
    }

    @Test
    fun clearFABResponse() {
        characterPresenter.onClickClearFAB()
        verify(view).showCharacters(emptyList())
    }
}