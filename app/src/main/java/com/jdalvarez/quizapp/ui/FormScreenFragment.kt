package com.jdalvarez.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.databinding.FragmentFormScreenBinding

class FormScreenFragment : Fragment() {
    private lateinit var binding:FragmentFormScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFormScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnJugar.setOnClickListener { play() }
    }

    private fun validateFormData(name: String?, lastName: String?, email:String?): Boolean{
        var isValid = true
        if(name.isNullOrEmpty()){
            binding.etName.error = " Ingrese su Nombre"
            isValid=false
        }
        if(lastName.isNullOrEmpty()){
            binding.etLastName.error = "Ingrese su Apellido"
            isValid=false
        }
        if(email.isNullOrEmpty()){
            binding.etEmail.error = "Ingrese su email"
            isValid=false
        }

        return isValid
    }

    private fun play(){
        val name = binding.etName.text.toString().trim()
        val lastName =binding.etLastName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (validateFormData(name,lastName,email)){
            toQuizzFragment()
        }
    }

    private fun toQuizzFragment(){
        val toQuizzAction = FormScreenFragmentDirections.actionFormScreenToQuizzFragment2()
        findNavController().navigate(toQuizzAction)

    }


}