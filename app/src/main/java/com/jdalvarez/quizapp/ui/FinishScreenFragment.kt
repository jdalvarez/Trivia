package com.jdalvarez.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.databinding.FragmentFinishScreenBinding


class FinishScreenFragment : Fragment() {
   private lateinit var binding:FragmentFinishScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWinnerAnimation(binding.animContent,R.raw.winner,true)
    }

    private fun setWinnerAnimation(imageView: LottieAnimationView, animation: Int, win: Boolean){
        binding.animContent.apply {
            setAnimation(animation)
            playAnimation()
        }
    }
}