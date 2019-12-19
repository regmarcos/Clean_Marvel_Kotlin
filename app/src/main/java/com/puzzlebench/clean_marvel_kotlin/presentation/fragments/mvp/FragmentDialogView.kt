package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import android.view.View
import android.widget.Toast
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.TAG
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.progress_fragment_bar
import java.lang.ref.WeakReference

class FragmentDialogView(activity: MainActivity) : FragmentDialogContracts.View {
    private val activityRef = WeakReference(activity)

    fun init(fragment: CharacterFragmentDialog){
        showLoading(fragment)
    }

    override fun showDialogFragment(characterFragment: CharacterFragmentDialog) {
        val fragmentManager = activityRef.get()?.supportFragmentManager
        characterFragment.show(fragmentManager,TAG)
    }

    override fun showToastNoItem() {
        val activity = activityRef.get()
        val message = activity?.baseContext?.resources?.getString(R.string.no_item_to_show)
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(fragment: CharacterFragmentDialog) {
        fragment.progress_fragment_bar.visibility = View.VISIBLE

    }

    override fun hideLoading(fragment: CharacterFragmentDialog) {
        fragment.progress_fragment_bar.visibility = View.GONE
    }
}
