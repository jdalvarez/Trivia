package com.jdalvarez.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.data.User
import com.jdalvarez.quizapp.databinding.FragmentFinishScreenBinding
import com.jdalvarez.quizapp.presentation.FinishScreenViewModel
import com.jdalvarez.quizapp.presentation.FinishScreenViewModelFactory
import com.jdalvarez.quizapp.util.QuizConfig


class FinishScreenFragment : Fragment() {
    private val app by lazy { requireActivity().application as MainApplication }
    private val viewModel by viewModels<FinishScreenViewModel> { FinishScreenViewModelFactory(app.respository) }
    private lateinit var binding:FragmentFinishScreenBinding
    private val args: FinishScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.onViewCreated(args.email, args.score)

    }
    private fun setUpObservers(){
        viewModel.userWinner.observe(viewLifecycleOwner){
            setAnimation(it)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            showLoading()
        }
        viewModel.userLiveData.observe(viewLifecycleOwner){
            showUserInfo(it)
        }

    }

    private fun showUserInfo(user: User?) {
        if (user != null) {
            binding.tvUserName.text =user.firstName
        }
        binding.tvScore.text = "Score: ${args.score.toString()}/${QuizConfig.QUESTIONS_NUMBER}"
    }

    private fun showLoading() {
    }

    private fun setAnimation(userWon:Boolean){
        if(userWon){
            setWinnerAnimation(binding.animContent,R.raw.winner, true)
        }else{
            setLooserAnimation(binding.animContent, R.raw.studyly, true)
        }
    }

    private fun setWinnerAnimation(imageView: LottieAnimationView, animation: Int, win: Boolean){
        binding.animContent.apply {
            setAnimation(animation)
            playAnimation()
        }
    }

    private fun setLooserAnimation(imageView: LottieAnimationView, animation: Int, win: Boolean){
        binding.animContent.apply {
            setAnimation(animation)
            playAnimation()
        }
    }
}