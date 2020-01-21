package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.TAG
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import kotlinx.android.synthetic.main.activity_main.clear_fab
import kotlinx.android.synthetic.main.activity_main.database_fab
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.recycleView
import kotlinx.android.synthetic.main.activity_main.refresh_fab
import java.lang.ref.WeakReference

class CharacterView(activity: MainActivity) {
    private val activity = WeakReference(activity).get()
    private val SPAN_COUNT = 1
    var adapter = CharacterAdapter { character -> showFragmentDialog(character) }

    fun init() {
        activity?.let {
                it.recycleView.layoutManager = GridLayoutManager(it, SPAN_COUNT)
                it.recycleView.adapter = adapter
                showLoading()
        }

    }

    fun showToastNoItemToShow() {
       activity?.let {
            val message = it.baseContext.resources.getString(R.string.message_no_items_to_show)
            it.applicationContext.showToast(message)
        }
    }

    fun showToastNetworkError(error: String) {
        activity?.applicationContext?.showToast(error)
    }

    fun hideLoading() {
        activity?.let{ it.progressBar.visibility = View.GONE }
    }

    fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    fun showLoading() {
        activity?.let { it.progressBar.visibility = View.VISIBLE }
    }

    fun showFAB() {
       activity?.let { it.refresh_fab.visibility = View.VISIBLE }
       activity?.let { it.database_fab.visibility = View.VISIBLE }
       activity?.let { it.clear_fab.visibility = View.VISIBLE }
    }

    fun hideFAB() {
       activity?.let { it.refresh_fab.visibility = View.GONE }
       activity?.let { it.database_fab.visibility = View.GONE }
       activity?.let { it.clear_fab.visibility = View.GONE }
    }

    private fun showFragmentDialog(character: Character){
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
           activity?.let { CharacterFragmentDialog.newInstance(character, it) }?.show(it, TAG)
        }
    }
}
