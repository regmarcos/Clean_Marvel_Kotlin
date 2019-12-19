package com.puzzlebench.clean_marvel_kotlin.presentation.fragments


import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.DOT
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.NO_DESCRIPTION
import com.puzzlebench.clean_marvel_kotlin.presentation.SCREEN_PERCENTAGE
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogModel
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogView
import com.puzzlebench.cmk.data.service.CharacterServicesImpl
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetSingleCharacterServiceUseCase
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.view.character_name
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.view.frag_desc
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.view.image_view
import kotlinx.android.synthetic.main.fragment_character_fragment_dialog.view.vertical_layout

/**
 * A simple [Fragment] subclass.
 */
class CharacterFragmentDialog : DialogFragment() {

    private val singleCharacterUseCase = GetSingleCharacterServiceUseCase(CharacterServicesImpl(), character.id)
    private val presenter = FragmentDialogPresenter(FragmentDialogView(mainActivity), FragmentDialogModel(singleCharacterUseCase))

    companion object {
        private lateinit var character: Character
        private lateinit var mainActivity: MainActivity
        fun newInstance(character: Character, activity: MainActivity?): CharacterFragmentDialog {
            this.character = character
            mainActivity = activity!!
            return CharacterFragmentDialog()
        }
    }

    fun init() {
        presenter.init(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frag = inflater.inflate(R.layout.fragment_character_fragment_dialog, container)
        frag.character_name.text = character.name
        frag.frag_desc.text = if (character.description.isNotEmpty()) character.description else NO_DESCRIPTION
        val url = "${character.thumbnail.path}$DOT${character.thumbnail.extension}"
        frag.image_view.getImageByUrl(url)
        val size = Point()
        (mainActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        frag.vertical_layout.layoutParams.width = (size.x * SCREEN_PERCENTAGE).toInt()
        presenter.view.hideLoading(this)
        return frag
    }

    override fun onResume() {
        super.onResume() }
}
