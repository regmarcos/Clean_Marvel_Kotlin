package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetSingleCharacterServiceUseCase
import io.reactivex.Single

class FragmentDialogModel(private val getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase): FragmentDialogContracts.Model {
    override fun getSingleCharacterServiceUseCase(): Single<List<Character>> = getSingleCharacterServiceUseCase.invoke()
}