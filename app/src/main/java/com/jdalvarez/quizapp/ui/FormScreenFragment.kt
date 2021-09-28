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
import com.google.firebase.firestore.FirebaseFirestore
import com.jdalvarez.quizapp.MainApplication
import com.jdalvarez.quizapp.data.Modalidad
import com.jdalvarez.quizapp.data.User
import com.jdalvarez.quizapp.databinding.FragmentFormScreenBinding
import com.jdalvarez.quizapp.presentation.FormScreenFragmentViewModel
import com.jdalvarez.quizapp.presentation.FormScreenFragmentViewModelFactory


class FormScreenFragment : Fragment() {
    private val app by lazy { requireActivity().application  as MainApplication }
    private val viewModel by viewModels<FormScreenFragmentViewModel> {
        FormScreenFragmentViewModelFactory(app.respository)
    }

    private lateinit var binding:FragmentFormScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFormScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setupForm()
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document()
        setupSpinner()
        binding.btnJugar.setOnClickListener { onPlayClicked()}
    }

    private fun setupForm() {
        binding.etName.setText("Juan")
        binding.etLastName.setText("Alvarez")
        binding.etEmail.setText("juan@trazzo.com.ar")
        onPlayClicked()
    }

    private fun validateFormData(nameView: EditText, lastNameView: EditText, emailView:EditText, dniView:EditText, carreraView:EditText): Boolean{
        var isValid = true
        if(nameView.text.isNullOrEmpty()){
            nameView.error = " Ingrese su Nombre"
            isValid=false
        }
        if(lastNameView.text.isNullOrEmpty()){
            binding.etLastName.error = "Ingrese su Apellido"
            isValid=false
        }
        if(emailView.text.isNullOrEmpty()){
            binding.etEmail.error = "Ingrese su email"
            isValid=false
        }
        if(dniView.text.isNullOrEmpty()){
            binding.etDNI.error = "Ingrese su dni"
            isValid=false
        }
        if(carreraView.text.isNullOrEmpty()){
            binding.spnCarreras.error = "Elija una carrera"
            isValid=false
        }

        return isValid
    }

    private fun onPlayClicked(){
        val email = binding.etEmail
        if (validateFormData(binding.etName,binding.etLastName,email,binding.etDNI,binding.spnCarreras)){
            openQuizFragment(email.editableText.toString())
        }
        viewModel.saveUser(getUserObject())
    }

    private fun openQuizFragment(email:String){
        val toQuizzAction = FormScreenFragmentDirections.actionFormScreenToQuizzFragment2(email)
        findNavController().navigate(toQuizzAction)

    }

    private fun setupSpinner(){
        val spinner = binding.spnCarreras
        val listaCarreras = listOf("Abogacía",
                "Contador Público",
                "Escribanía",
                "Ing. en Software",
                "Lic .en Publicidad",
                "Lic. en Administración",
                "Lic. en Administración Agraria",
                "Lic. en Administración Hotelera",
                "Lic. en Administración Pública",
                "Lic. en Ambiente y Energía Renovable",
                "Lic. en Ciencia Política",
                "Lic. en Comercialización",
                "Lic. en Comercio Internacional",
                "Lic. en Criminología y Seguridad",
                "Lic. en Diseño de Indum.y Textil",
                "Lic. en Diseño Gráfico",
                "Lic. en Diseño Industrial",
                "Lic. en Diseño y Animación Digital",
                "Lic. en Educación (ciclo)",
                "Lic. en Gestión Ambiental",
                "Lic. en Gestión Recursos Humanos",
                "Lic. en Gestión Turística",
                "Lic. en Higiene y Seguridad Laboral",
                "Lic. en Informática",
                "Lic. en Periodismo",
                "Lic. en Psicología",
                "Lic. en Relaciones Internacionales",
                "Lic. en RRPP e Institucionales",
                "Lic. en Sociología",
                "Martillero y/o Corredor Público y/o Corredor Inmobiliario",
                "Procurador",
                "Tec. en Administración y Gestión de Políticas Públicas",
                "Tec. en Administración y Gestión Tributaria",
                "Tec. en Dir.de Protocolo, Org. de Eventos y RRPP",
                "Tec. en Dirección de Equipos de Venta",
                "Tec. en Diseño y Animación Digital",
                "Tec. en Gestión Contable e Impositiva",
                "Tec. en Gestión de Empresas Familiares",
                "Tec. en Gestión de Moda",
                "Tec. en Gestión del Clima Laboral en la Organización",
               " Tec. en Hidrocarburos y Geociencias",
                "Tec. en Higiene y Seguridad Laboral",
                "Tec. en Investigación de Escena del Crimen",
                "Tec. en Marketing y Publicidad",
                "Tec. en Promoción Comunitaria en Niñez y Adolescencia",
                "Tec. en Relaciones Laborales",
                "Tec. en Responsabilidad y Gestión Social",
                "Tec. Univ. En Gestión de Auditorías Ambientales",
                "Tec. Univ. En Gestión de Recursos Turísticos",
                "Tec. Univ. En Gestión Administrativa de Servicios de Salud",
                "Esp. En Finanzas Corporativas y Mercados de Capitales",
                "Esp. En Gestión y Gobierno de Empresas Familiares",
                "Esp. En Marketing y Dirección Comercial",
                "Maestría en Derecho Procesal",
                "Maestría en Administración de Empresas"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,listaCarreras)
        spinner.setAdapter(adapter)
    }

    private fun getUserObject(): User {
        val name = binding.etName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val dni = binding.etDNI.text.toString().trim()
        val  email = binding.etEmail.editableText.toString()
        val carrera = binding.spnCarreras.text.toString().trim()
        val modalidad = if(binding.modalidad.checkedRadioButtonId == 0) Modalidad.PRESENCIAL else Modalidad.DISTANCIA
        val user = User(name,lastName,dni,email,carrera,modalidad)
        return user
    }


}