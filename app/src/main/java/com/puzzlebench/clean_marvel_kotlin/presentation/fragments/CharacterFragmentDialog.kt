package com.puzzlebench.clean_marvel_kotlin.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogModel
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.fragments.mvp.FragmentDialogView
import com.puzzlebench.cmk.data.service.CharacterServicesImpl
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetSingleCharacterServiceUseCase

/**
 * A simple [Fragment] subclass.
 */
class CharacterFragmentDialog : DialogFragment() {

    private val singleCharacterUseCase = GetSingleCharacterServiceUseCase(CharacterServicesImpl(), character.id)
    private val presenter = FragmentDialogPresenter(FragmentDialogView(mainActivity), FragmentDialogModel(singleCharacterUseCase))

    companion object {
        private lateinit var character: Character
        private lateinit var mainActivity: MainActivity
        fun newInstance(character: Character, activity: MainActivity): CharacterFragmentDialog {
            this.character = character
            mainActivity = activity
            return CharacterFragmentDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_fragment_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init(this)
        super.onViewCreated(view, savedInstanceState)
    }
}
