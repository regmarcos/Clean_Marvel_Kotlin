package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.TAG
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import kotlinx.android.synthetic.main.activity_main.database_fab
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.recycleView
import kotlinx.android.synthetic.main.activity_main.refresh_fab
import java.lang.ref.WeakReference

class CharacterView(activity: MainActivity) {
    private val activityRef = WeakReference(activity)
    private val SPAN_COUNT = 1
    var adapter = CharacterAdapter { character -> showFragmentDialog(character) }

    fun init() {
        activityRef.get()?.let {
                it.recycleView.layoutManager = GridLayoutManager(it, SPAN_COUNT)
                it.recycleView.adapter = adapter
                showLoading()
        }

    }

    fun showToastNoItemToShow() {
       activityRef.get()?.let {
            val message = it.baseContext.resources.getString(R.string.message_no_items_to_show)
            it.applicationContext.showToast(message)
        }
    }

    fun showToastNetworkError(error: String) {
        activityRef.get()!!.applicationContext.showToast(error)
    }

    fun hideLoading() {
        activityRef.get()?.let{ it.progressBar.visibility = View.GONE }
    }

    fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    fun showLoading() {
        activityRef.get()?.let { it.progressBar.visibility = View.VISIBLE }
    }

    fun showFAB() {
        activityRef.get()?.let { it.refresh_fab.visibility = View.VISIBLE }
        activityRef.get()?.let { it.database_fab.visibility = View.VISIBLE }
    }

    fun hideFAB() {
        activityRef.get()?.let { it.refresh_fab.visibility = View.GONE }
        activityRef.get()?.let { it.database_fab.visibility = View.VISIBLE }
    }

    private fun showFragmentDialog(character: Character){
        val fragmentManager = activityRef.let{it.get()?.supportFragmentManager}
        fragmentManager?.let {
            activityRef.get()?.let { CharacterFragmentDialog.newInstance(character, it) }?.show(it, TAG)
        }
    }
}
