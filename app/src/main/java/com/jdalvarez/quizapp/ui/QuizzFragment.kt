package com.jdalvarez.quizapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.jdalvarez.quizapp.databinding.FragmentQuizzBinding
import com.jdalvarez.quizapp.presentation.QuizzViewModel
import androidx.fragment.app.viewModels
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.data.Constants
import com.jdalvarez.quizapp.presentation.QuizzViewModelFactory
import com.jdalvarez.quizapp.repository.FileDataSource
import com.jdalvarez.quizapp.repository.RepositoryImpl


class QuizzFragment : Fragment() {

    private val app by lazy { requireActivity().application as MainApplication }

    private val viewModel by viewModels<QuizzViewModel> {
        QuizzViewModelFactory(app.respository)
    }
    private lateinit var binding: FragmentQuizzBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val questionsList = Constants.getQuestions()
        Log.d("question", "son ${questionsList.size}")
        return binding.root
    }

    fun setupObservers() {
        binding.radioGroup.addView(RadioButton(requireContext()).apply {

        })

    }

    fun onNextClicked() {
        var selected = binding.radioGroup.checkedRadioButtonId

    }

}