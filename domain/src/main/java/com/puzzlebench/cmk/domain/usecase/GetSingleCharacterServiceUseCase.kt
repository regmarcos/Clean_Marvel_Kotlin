package com.puzzlebench.cmk.domain.usecase

import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.service.CharacterServices
import io.reactivex.Single

open class GetSingleCharacterServiceUseCase(private val characterServiceImp: CharacterServices, private val id: Int?) {
    open operator fun invoke(): Single<List<Character>> = characterServiceImp.getCharactersById(id)
}