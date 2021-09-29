package com.jdalvarez.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.R
import com.jdalvarez.quizapp.data.Modalidad
import com.jdalvarez.quizapp.data.User
import com.jdalvarez.quizapp.databinding.FragmentFormScreenBinding
import com.jdalvarez.quizapp.presentation.FormScreenFragmentViewModel
import com.jdalvarez.quizapp.presentation.FormScreenFragmentViewModelFactory


class FormScreenFragment : Fragment() {
    private val app by lazy { requireActivity().application as MainApplication }
    private val viewModel by viewModels<FormScreenFragmentViewModel> {
        FormScreenFragmentViewModelFactory(app.respository)
    }

    private lateinit var binding: FragmentFormScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupUi()
        viewModel.loadDegrees()
    }

    private fun setupObservers() {
        viewModel.degreesLiveData.observe(viewLifecycleOwner) {
            setupSpinner(it)
        }
    }

    private fun setupUi() {
        binding.btnJugar.setOnClickListener { onPlayClicked() }
    }

    private fun validateFormData(
        nameView: EditText,
        lastNameView: EditText,
        emailView: EditText,
        dniView: EditText,
        carreraView: EditText
    ): Boolean {
        var isValid = true
        if (nameView.text.isNullOrEmpty()) {
            nameView.error = " Ingrese su Nombre"
            isValid = false
        }
        if (lastNameView.text.isNullOrEmpty()) {
            binding.etLastName.error = "Ingrese su Apellido"
            isValid = false
        }
        if (emailView.text.isNullOrEmpty()) {
            binding.etEmail.error = "Ingrese su email"
            isValid = false
        }
        if (dniView.text.isNullOrEmpty()) {
            binding.etDNI.error = "Ingrese su dni"
            isValid = false
        }
        if (carreraView.text.isNullOrEmpty()) {
            binding.spnCarreras.error = "Elija una carrera"
            isValid = false
        }

        return isValid
    }

    private fun onPlayClicked() {
        val email = binding.etEmail
        if (validateFormData(
                binding.etName,
                binding.etLastName,
                email,
                binding.etDNI,
                binding.spnCarreras
            )
        ) {
            openQuizFragment(email.editableText.toString())
        }
        viewModel.saveUser(getUserObject())
    }

    private fun openQuizFragment(email: String) {
        val toQuizzAction = FormScreenFragmentDirections.actionFormScreenToQuizzFragment2(email)
        findNavController().navigate(toQuizzAction)
    }

    private fun setupSpinner(degrees: List<String>) {
        val spinner = binding.spnCarreras
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item_custom, degrees)
        spinner.setAdapter(adapter)
    }

    private fun getUserObject(): User = User(
        binding.etName.text.toString().trim(),
        binding.etLastName.text.toString().trim(),
        binding.etDNI.text.toString().trim(),
        binding.etEmail.editableText.toString(),
        binding.spnCarreras.text.toString().trim(),
        if (binding.modalidad.checkedRadioButtonId == 0) Modalidad.PRESENCIAL else Modalidad.DISTANCIA
    )

    private fun hack() {
        binding.etName.setText("Juan")
        binding.etLastName.setText("Alvarez")
        binding.etEmail.setText("juan@trazzo.com.ar")
        onPlayClicked()
    }

}