package com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.DOT
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.SCREEN_PERCENTAGE
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.CharacterFragmentDialog
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.contracts.FragmentDialogContracts
import com.puzzlebench.cmk.domain.model.Character
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.character_name
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.frag_description
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.image_view
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.progress_bar_fragment
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.vertical_layout
import java.lang.ref.WeakReference

class FragmentDialogView(private val activity: MainActivity) : FragmentDialogContracts.View {
    private val activityRef = WeakReference(activity)

    override fun showDialogFragment(characterFragment: CharacterFragmentDialog, character: Character) {
        characterFragment.character_name.text = character.name
        characterFragment.frag_description.text = character.description
        val url = "${character.thumbnail?.path}$DOT${character.thumbnail?.extension}"
        characterFragment.image_view.getImageByUrl(url)
        val size = Point()
        (activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        characterFragment.vertical_layout.layoutParams.width = (size.x * SCREEN_PERCENTAGE).toInt()
    }

    override fun showToastNoItem() {
        val activity = activityRef.get()
        val message = activity?.baseContext?.resources?.getString(R.string.no_item_to_show)
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(fragment: CharacterFragmentDialog) {
        fragment.progress_bar_fragment.visibility = View.VISIBLE
    }

    override fun hideLoading(fragment: CharacterFragmentDialog) {
        fragment.progress_bar_fragment.visibility = View.GONE
    }

    override fun showToastNetworkError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
