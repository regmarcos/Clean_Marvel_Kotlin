package com.puzzlebench.clean_marvel_kotlin.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatCallback
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.contracts.CharacterContracts
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import com.puzzlebench.cmk.data.mapper.repository.CharacterMapperRepository
import com.puzzlebench.cmk.data.repository.CharacterDataRepository
import com.puzzlebench.cmk.data.repository.source.CharacterDataSourceImpl
import com.puzzlebench.cmk.data.service.CharacterServicesImpl
import com.puzzlebench.cmk.domain.usecase.GetCharacterRepositoryUseCase
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import kotlinx.android.synthetic.main.activity_main.clear_fab
import kotlinx.android.synthetic.main.activity_main.database_fab
import kotlinx.android.synthetic.main.activity_main.refresh_fab
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : BaseRxActivity(), AppCompatCallback {

    private val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    private val getCharacterRepositoryUseCase = GetCharacterRepositoryUseCase(CharacterDataRepository(CharacterDataSourceImpl(), CharacterMapperRepository()))
    private val saveCharacterRepositoryUseCase = SaveCharacterRepositoryUseCase(CharacterDataRepository(CharacterDataSourceImpl(), CharacterMapperRepository()))

    private val presenter: CharacterContracts.Presenter = CharacterPresenter(CharacterView(this),
            getCharacterServiceUseCase,
            getCharacterRepositoryUseCase,
            saveCharacterRepositoryUseCase,
            subscriptions)

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context?): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
        setListeners()
        setSupportActionBar(toolbar)
    }

    private fun setListeners() {
        refresh_fab.setOnClickListener { presenter.onClickRefreshFAB() }
        database_fab.setOnClickListener { presenter.onClickDatabaseFAB() }
        clear_fab.setOnClickListener { presenter.onClickClearFAB() }
    }
}
