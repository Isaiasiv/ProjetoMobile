package com.example.projetomobile.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MateriaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    fun criarMateria(nomeMateria: String, descricaoMateria: String, callback: (Boolean, String?) -> Unit) {
        val usuarioId = usuarioId
        if (usuarioId == null) {
            callback(false, "Usuário não autenticado")
            return
        }

        val materia = hashMapOf(
            "nome" to nomeMateria,
            "descricao" to descricaoMateria
        )

        db.collection("Usuarios").document(usuarioId)
            .collection("materias")
            .add(materia)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Matéria criada com ID: ${documentReference.id}")
                callback(true, documentReference.id)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao criar matéria", e)
                callback(false, e.message)
            }
    }


    fun buscarMaterias(usuarioId: String, callback: (List<Map<String, Any>>?, String?) -> Unit) {
        db.collection("Usuarios").document(usuarioId)
            .collection("materias")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val materias = task.result?.documents?.map {
                        it.data?.plus("id" to it.id) ?: emptyMap()
                    } ?: emptyList()
                    Log.d("Firestore", "Matérias encontradas: $materias")
                    callback(materias, null)
                } else {
                    Log.w("Firestore", "Erro ao buscar matérias", task.exception)
                    callback(null, task.exception?.message)
                }
            }
    }


}
