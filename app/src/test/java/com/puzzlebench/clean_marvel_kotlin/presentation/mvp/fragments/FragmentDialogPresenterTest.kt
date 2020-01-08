package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragments

import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogModel
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogView
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.service.CharacterServices
import com.puzzlebench.cmk.domain.usecase.GetSingleCharacterServiceUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class FragmentDialogPresenterTest {

    private val view = mock(FragmentDialogView::class.java)
    private val characterServiceImp = mock(CharacterServices::class.java)
    private val fragmentDialog = mock(CharacterFragmentDialog::class.java)
    private lateinit var getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase
    private lateinit var model: FragmentDialogModel
    private lateinit var presenter: FragmentDialogPresenter

    companion object {
        const val EMPTY_STRING = ""
        const val ID_EXAMPLE = 0

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val immediate = object : Scheduler() {
                var noDelay = ID_EXAMPLE
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    return super.scheduleDirect(run, noDelay.toLong(), unit) // Prevents StackOverflowErrors when scheduling with a delay
                }

                override fun createWorker(): Scheduler.Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            // These avoid ExceptionInInitializerError when testing methods that contains RxJava Schedulers
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(characterServiceImp, ID_EXAMPLE)
        model = FragmentDialogModel(getSingleCharacterServiceUseCase)
        presenter = FragmentDialogPresenter(view,model)
    }

    @Test
    fun init() {
        val item = getCharacter()
        val observable = Single.just(item)
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(observable)
        presenter.init(fragmentDialog)
        verify(characterServiceImp).getCharactersById(item[0].id)
        verify(view).hideLoading(fragmentDialog)
    }

    @Test
    fun reposeWithError() {
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(Single.error(Exception(EMPTY_STRING)))
        presenter.init(fragmentDialog)
        verify(characterServiceImp).getCharactersById(ID_EXAMPLE)
        verify(view).hideLoading(fragmentDialog)
        verify(view).showToastNetworkError(EMPTY_STRING)
    }

    @Test
    fun reposeWithItem() {
        val item = getCharacter()
        val observable = Single.just(item)
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(observable)
        presenter.init(fragmentDialog)
        verify(characterServiceImp).getCharactersById(item.last().id)
        verify(view).hideLoading(fragmentDialog)
        verify(view).showDialogFragment(fragmentDialog, item.last())
    }

    @Test
    fun reposeWithNoItem() {
        val item = emptyList<Character>()
        val observable = Single.just(item)
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(observable)
        presenter.init(fragmentDialog)
        verify(characterServiceImp).getCharactersById(ID_EXAMPLE)
        verify(view).hideLoading(fragmentDialog)
        verify(view).showToastNoItem()
    }

    private fun getCharacter(): List<Character> = listOf(1..1).map{
        mock(Character::class.java)
    }
}