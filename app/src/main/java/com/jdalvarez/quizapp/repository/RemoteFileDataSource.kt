package com.jdalvarez.quizapp.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RemoteFileDataSource(val context: Context) {
    fun saveUser(firstName: String, lastName: String, dni: String, email: String, carrera: String, modalidad:String, played:String){
        val db = Firebase.firestore
        db.collection("users").document(email).set(
            hashMapOf("nombre" to firstName,
                "apellido" to lastName,
                "dni" to dni,
                "email" to email,
                "carrera" to carrera,
                "modalidad" to modalidad,
                "jugo" to played
            )
        ).addOnFailureListener {
            Log.d("FAIL", it.message.toString())
        }
    }

}