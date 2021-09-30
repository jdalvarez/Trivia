package com.jdalvarez.quizapp.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.jdalvarez.quizapp.databinding.FragmentQuizzBinding
import com.jdalvarez.quizapp.presentation.QuizzViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.data.Question
import com.jdalvarez.quizapp.presentation.QuestionResult
import com.jdalvarez.quizapp.presentation.QuizzViewModelFactory
import com.jdalvarez.quizapp.util.QuizConfig
import com.jdalvarez.quizapp.util.QuizConfig.QUESTIONS_NUMBER


class QuizzFragment : Fragment() {

    private val app by lazy { requireActivity().application as MainApplication }
    private val viewModel by viewModels<QuizzViewModel> { QuizzViewModelFactory(app.respository) }
    private lateinit var binding: FragmentQuizzBinding
    private val args: QuizzFragmentArgs by navArgs()
    private var score:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzBinding.inflate(inflater, container, false)
        setOnClickListeners()
        setupUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.onViewCreated()
    }

    private fun setupUi() {
        btnUnSelected(binding.btnFalse)
        btnUnSelected(binding.btnTrue)
        binding.username.text = args.email
    }

    private fun btnUnSelected(btn: MaterialButton) {
        btn.apply {
            setBackgroundResource(R.drawable.un_press_btn_bg)
            setTextColor(resources.getColor(R.color.green))
        }
    }

    private fun btnSelected(btn: MaterialButton) {
        btn.apply {
            setBackgroundResource(R.drawable.press_btn_bg)
            setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun loadQuestionData(currentQuestion: Question) {
        binding.tvQuestion.text = currentQuestion.questionText
        binding.tvRta.text = currentQuestion.rightAnswer
        btnUnSelected(binding.btnFalse)
        btnUnSelected(binding.btnTrue)
    }

    private fun setOnClickListeners() {
        binding.btnTrue.setOnClickListener {
            btnSelected(binding.btnTrue)
            btnUnSelected(binding.btnFalse)
            viewModel.onUserAnswer(true)

        }
        binding.btnFalse.setOnClickListener {
            btnSelected(binding.btnFalse)
            btnUnSelected(binding.btnTrue)
            viewModel.onUserAnswer(false)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.btnFalse.isEnabled = !isLoading
        binding.btnFalse.isClickable = !isLoading
        binding.btnTrue.isEnabled = !isLoading
        binding.btnTrue.isClickable = !isLoading
    }

    private fun setupObservers() {
        viewModel.scoreLiveData.observe(viewLifecycleOwner) { binding.score.setText("Score: $it/${QUESTIONS_NUMBER}")
            score = it}
        viewModel.questionLiveData.observe(viewLifecycleOwner) { loadQuestionData(it) }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.navigateToFinishScreen.observe(viewLifecycleOwner) { finishQuizz(it) }
        viewModel.questionResultLiveData.observe(viewLifecycleOwner) { showAnswer(it) }
        viewModel.progressVisibleLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }
    }

    private fun showProgressBar(isInProgress: Boolean?) {
        val progressBar = binding.progressBar
        val currentProgress = 1000
        if(isInProgress == true){
            progressBar.setProgress(0)
            ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
                .setDuration(QuizConfig.SHOW_ANSWER_DELAY_MS)
                .start()
        }else{
            progressBar.setProgress(0)
        }
    }

    private fun showAnswer(questionResult: QuestionResult) {
        when (questionResult) {
            QuestionResult.RIGHT -> {
                binding.tvRta.apply {
                    visibility = View.VISIBLE
                    setTextColor(resources.getColor(R.color.green))
                    setWinnerAnimation(binding.animQuizContent,R.raw.correct_animation,true)
                }
            }
            QuestionResult.WRONG -> {
                binding.tvRta.apply {
                    visibility = View.VISIBLE
                    setTextColor(Color.RED)
                    setLooserAnimation(binding.animQuizContent, R.raw.wrong_animation,true)
                }
            }
            QuestionResult.NONE -> {
                binding.tvRta.visibility = View.INVISIBLE
            }
        }
    }
    private fun setLooserAnimation(imageView: LottieAnimationView, animation: Int, win: Boolean){
        imageView.apply {
            visibility = View.VISIBLE
            setAnimation(animation)
            playAnimation()
            addAnimatorListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    imageView.speed = -1f
                    imageView.visibility = View.GONE
                }
            })
        }
    }

    private fun setWinnerAnimation(imageView: LottieAnimationView, animation: Int, win: Boolean){
        imageView.apply {
            visibility = View.VISIBLE
            setAnimation(animation)
            playAnimation()
            addAnimatorListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    imageView.speed = -1f
                    imageView.visibility = View.GONE
                }
            })
        }
    }

    private fun toFinishScreen(email: String, score: Int) {
        val toFinishAction =
            QuizzFragmentDirections.actionQuizzFragmentToFinishScreenFragment(email,score)
        findNavController().navigate(toFinishAction)
    }

    private fun finishQuizz(finish: Boolean?) {
        if (finish != null) {
            if (finish) {
                toFinishScreen(args.email,score)
            }
        }
    }

    private fun validateUserEntry(): Boolean {
        // validate if user selected one option..;
        return true
    }


}