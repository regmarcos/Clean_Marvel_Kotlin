package com.puzzlebench.clean_marvel_kotlin.presentation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.cmk.data.mapper.repository.CharacterMapperRepository
import com.puzzlebench.cmk.data.repository.CharacterDataRepository
import com.puzzlebench.cmk.data.repository.source.CharacterDataSourceImpl
import com.puzzlebench.cmk.data.service.CharacterServicesImpl
import com.puzzlebench.cmk.domain.usecase.GetCharacterRepositoryUseCase
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterView
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import kotlinx.android.synthetic.main.activity_main.floating_action_button

class MainActivity : BaseRxActivity() {

    private val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    private val getCharacterRepositoryUseCase = GetCharacterRepositoryUseCase(CharacterDataRepository(CharacterDataSourceImpl(), CharacterMapperRepository()))
    private val saveCharacterRepositoryUseCase = SaveCharacterRepositoryUseCase(CharacterDataRepository(CharacterDataSourceImpl(), CharacterMapperRepository()))

    private val presenter = CharacterPresenter(CharacterView(this),
            getCharacterServiceUseCase,
            getCharacterRepositoryUseCase,
            saveCharacterRepositoryUseCase,
            subscriptions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
        floating_action_button.setOnClickListener { presenter.onClickFAB() }
    }
}
