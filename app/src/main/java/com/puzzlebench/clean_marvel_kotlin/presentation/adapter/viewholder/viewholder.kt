package com.puzzlebench.clean_marvel_kotlin.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.presentation.DOT
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import com.puzzlebench.clean_marvel_kotlin.presentation.listener.CharacterListener
import kotlinx.android.synthetic.main.character_cards_layout.view.*


class CharactersAdapterViewHolder(view: View, val listener: CharacterListener) : RecyclerView.ViewHolder(view) {

    fun bind(item: Character) = with(itemView) {
        tv_item.text = item.name
        val string = item.thumbnail?.run {
            "${path}$DOT${extension}"
        }
        string?.let { image_thumbnail.getImageByUrl(it) }
        setOnClickListener { listener(item) }
    }
}