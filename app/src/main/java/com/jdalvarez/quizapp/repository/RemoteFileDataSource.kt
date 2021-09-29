package com.jdalvarez.quizapp.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RemoteFileDataSource(val context: Context) {
    fun saveUser(
        firstName: String?,
        lastName: String?,
        dni: String?,
        email: String?,
        carrera: String?,
        modalidad: String?,
        played: String?
    ) {
        val db = Firebase.firestore
        email?.let {
            db.collection("users").document(email).set(
                hashMapOf(
                    "nombre" to firstName,
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

    fun savePlayResult(email: String, played: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(email).update(
            mutableMapOf<String, Any>("jugo" to played)
        )
            .addOnSuccessListener { Log.d(TAG, "User data correct charged") }
            .addOnFailureListener {
                Log.d("Error", it.message.toString())
            }
    }

    suspend fun getUser(email: String): DocumentSnapshot? {
        val db = FirebaseFirestore.getInstance()
        var result: DocumentSnapshot? = null
        withContext(Dispatchers.IO) {
            db.collection("users").document(email).get().addOnSuccessListener {
                result = it
            }.await()
        }
        return result
    }

}