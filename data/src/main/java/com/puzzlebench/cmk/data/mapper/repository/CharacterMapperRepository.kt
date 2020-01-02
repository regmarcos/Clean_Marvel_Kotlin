package com.puzzlebench.cmk.data.mapper.repository

import com.puzzlebench.cmk.data.model.CharacterRealm
import com.puzzlebench.cmk.data.model.ThumbnailRealm
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.model.Thumbnail

class CharacterMapperRepository : BaseMapperRepository<Character, CharacterRealm> {

    override fun transform(input: CharacterRealm): Character = Character(input.id, input.name, input.description, input.thumbnail?.let { transformToThumbnail(it) })

    override fun transform(input: Character): CharacterRealm = CharacterRealm(input.id, input.name, input.description, input.thumbnail?.let { transformToThumbnailRealm(it) })

    private fun transformToThumbnail(thumbnailRealm: ThumbnailRealm): Thumbnail = Thumbnail(thumbnailRealm.path!!, thumbnailRealm.extension!!)

    private fun transformToThumbnailRealm(thumbnail: Thumbnail): ThumbnailRealm = ThumbnailRealm(thumbnail.path, thumbnail.extension)
}
