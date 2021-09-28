package com.jdalvarez.quizapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jdalvarez.quizapp.databinding.FragmentQuizzBinding
import com.jdalvarez.quizapp.presentation.QuizzViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.presentation.QuizzViewModelFactory


class QuizzFragment : Fragment() {

    private val app by lazy { requireActivity().application  as MainApplication }

    private val viewModel by viewModels<QuizzViewModel> {
        QuizzViewModelFactory(app.respository)
    }

    private lateinit var binding: FragmentQuizzBinding

    private val args:QuizzFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setOnClickListeners()
        setupUi()
        return binding.root
    }

    private fun setupUi() {
        binding.score.setText("Score: 0/8")
        btnUnSelected(binding.btnFalse)
        btnUnSelected(binding.btnTrue)
        binding.username.text = args.email
    }

    private fun btnUnSelected(btn: MaterialButton) {
        btn.apply{
            setBackgroundResource(R.drawable.un_press_btn_bg)
            setTextColor(resources.getColor(R.color.green))
        }
    }
    private fun btnSelected(btn: MaterialButton) {
        btn.apply{
            setBackgroundResource(R.drawable.press_btn_bg)
            setTextColor(resources.getColor(R.color.white))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.onViewCreated()
    }

    private fun loadQuestionData(currentQuestion:Question) {
        binding.tvQuestion.setText(currentQuestion.questionText)
        btnUnSelected(binding.btnFalse)
        btnUnSelected(binding.btnTrue)

    }

    private fun setOnClickListeners(){
        binding.btnTrue.setOnClickListener {
            btnSelected(binding.btnTrue)
            btnUnSelected(binding.btnFalse)
            viewModel.checkAnswer(true)

        }
        binding.btnFalse.setOnClickListener {
            btnSelected(binding.btnFalse)
            btnUnSelected(binding.btnTrue)
            viewModel.checkAnswer(false)
        }

    }

    private fun showLoading(isLoading: Boolean) {
    }

    private fun setupObservers() {
        viewModel.scoreLiveData.observe(viewLifecycleOwner){
            binding.score.setText("Score: $it/8")
        }

        viewModel.questionLiveData.observe(viewLifecycleOwner) {
            loadQuestionData(it)
        }
        viewModel.LoadingLiveData.observe(viewLifecycleOwner) {
            // show/hide Loading on top of questions
            // disable/enable next button
        }
        viewModel.navigateToFinishScreen.observe(viewLifecycleOwner){
            finishQuizz(it)
        }
    }


    private fun toFinishScreen(email:String){
        val toFinishAction = QuizzFragmentDirections.actionQuizzFragmentToFinishScreenFragment(email)
        findNavController().navigate(toFinishAction)
    }

    private fun finishQuizz(finish:Boolean?){
        if (finish!=null) {
            if (finish){
                toFinishScreen(args.email)
            }
        }

    }

    private fun validateUserEntry(): Boolean {
        // validate if user selected one option..;
        return true
    }


}