package com.example.projetomobile.domain

import com.example.projetomobile.data.MateriaRepository
import com.example.projetomobile.domain.Materia

class BuscarMateriasUseCase(private val materiaRepository: MateriaRepository) {

    fun execute(usuarioId: String, callback: (List<Materia>?, String?) -> Unit) {
        materiaRepository.buscarMaterias(usuarioId) { materiasMap, mensagem ->
            if (materiasMap != null) {
                val materias = materiasMap.map {
                    Materia(
                        id = it["id"] as? String ?: "",
                        nome = it["nome"] as? String ?: "",
                        descricao = it["descricao"] as? String ?: ""
                    )
                }
                callback(materias, null)
            } else {
                callback(null, mensagem)
            }
        }
    }
}
