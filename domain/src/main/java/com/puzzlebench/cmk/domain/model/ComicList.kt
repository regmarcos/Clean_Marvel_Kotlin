package com.puzzlebench.cmk.domain.model

open class ComicList(
        var available: Int,
        var returned: Int,
        var collectionURI: String,
        var items: MutableList<ComicSummary>

)