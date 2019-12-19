package com.puzzlebench.cmk.data.service.response

import com.puzzlebench.cmk.domain.model.ComicList

class CharacterResponse (
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: ThumbnailResponse//,
        //val comics: ComicList
)