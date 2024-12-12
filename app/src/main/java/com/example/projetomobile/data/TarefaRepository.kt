package com.example.projetomobile.data

import android.util.Log
import com.example.projetomobile.domain.Tarefa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TarefaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid // Obtém o ID do usuário autenticado dinamicamente

    fun criarTarefa(
        materiaId: String,
        tarefa: Tarefa,
        callback: (Boolean, String?) -> Unit
    ) {
        val usuarioId = usuarioId // Recupera o ID do usuário
        if (usuarioId == null) {
            callback(false, "Usuário não autenticado")
            return
        }

        val tarefaMap = hashMapOf(
            "nome" to tarefa.nome,
            "titulo" to tarefa.titulo,
            "objetivo" to tarefa.objetivo,
            "descricao" to tarefa.descricao,
            "dataInicio" to tarefa.dataInicio,
            "dataFim" to tarefa.dataFim
        )

        db.collection("Usuarios")
            .document(usuarioId) // Usa o ID do usuário autenticado
            .collection("materias")
            .document(materiaId)
            .collection("tarefas")
            .add(tarefaMap)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Tarefa criada com ID: ${documentReference.id}")
                callback(true, documentReference.id)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao criar tarefa", e)
                callback(false, e.message)
            }
    }

    fun buscarTarefas(
        usuarioId: String,
        materiaId: String,
        callback: (List<Map<String, Any>>?, String?) -> Unit
    ) {
        db.collection("Usuarios").document(usuarioId)
            .collection("materias").document(materiaId)
            .collection("tarefas")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val tarefas = task.result?.documents?.map {
                        it.data?.plus("id" to it.id) ?: emptyMap()
                    } ?: emptyList()
                    Log.d("Firestore", "Tarefas encontradas: $tarefas")
                    callback(tarefas, null)
                } else {
                    Log.w("Firestore", "Erro ao buscar tarefas", task.exception)
                    callback(null, task.exception?.message)
                }
            }
    }


    fun buscarUmaTarefa(
        usuarioId: String,
        materiaId: String,
        tarefaId: String,
        callback: (Map<String, Any>?, String?) -> Unit
    ) {
        db.collection("Usuarios").document(usuarioId)
            .collection("materias").document(materiaId)
            .collection("tarefas").document(tarefaId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val tarefa = document.data
                    Log.d("Firestore", "Tarefa encontrada: $tarefa")
                    callback(tarefa, null)
                } else {
                    Log.w("Firestore", "Tarefa não encontrada")
                    callback(null, "Tarefa não encontrada")
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao buscar tarefa", e)
                callback(null, e.message)
            }
    }

    fun editarTarefa(
        usuarioId: String,
        materiaId: String,
        tarefaId: String,
        novoTitulo: String,
        callback: (Boolean, String?) -> Unit
    ) {
        db.collection("Usuarios").document(usuarioId)
            .collection("materias").document(materiaId)
            .collection("tarefas").document(tarefaId)
            .update("titulo", novoTitulo)
            .addOnSuccessListener {
                Log.d("Firestore", "Tarefa atualizada")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao atualizar tarefa", e)
                callback(false, e.message)
            }
    }

    fun excluirTarefa(
        usuarioId: String,
        materiaId: String,
        tarefaId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        db.collection("usuarios").document(usuarioId)
            .collection("materias").document(materiaId)
            .collection("tarefas").document(tarefaId).delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Tarefa deletada")
                callback(true, null)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao deletar tarefa", e)
                callback(false, e.message)
            }
    }

        fun getAllTarefas(onComplete: (List<Tarefa>, String?) -> Unit) {
            val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
            if (usuarioId == null) {
                onComplete(emptyList(), "Usuário não autenticado")
                return
            }

            val db = FirebaseFirestore.getInstance()
            db.collection("Usuarios").document(usuarioId)
                .collection("materias")
                .get()
                .addOnSuccessListener { materiasSnapshot ->
                    val tarefas = mutableListOf<Tarefa>()
                    var processed = 0
                    for (materiaDoc in materiasSnapshot.documents) {
                        val materiaId = materiaDoc.id
                        db.collection("Usuarios").document(usuarioId)
                            .collection("materias").document(materiaId)
                            .collection("tarefas")
                            .get()
                            .addOnSuccessListener { tarefasSnapshot ->
                                for (tarefaDoc in tarefasSnapshot.documents) {
                                    val tarefa = tarefaDoc.toObject(Tarefa::class.java)
                                    tarefa?.let { tarefas.add(it) }
                                }
                                processed++
                                if (processed == materiasSnapshot.size()) {
                                    onComplete(tarefas, null)
                                }
                            }
                            .addOnFailureListener { e ->
                                onComplete(emptyList(), "Erro ao buscar tarefas: ${e.message}")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    onComplete(emptyList(), "Erro ao buscar matérias: ${e.message}")
                }
        }
    }
