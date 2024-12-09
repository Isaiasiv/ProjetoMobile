package com.example.projetomobile.domain

import com.example.projetomobile.data.MateriaRepository

class BuscarMateriasUseCase(private val materiaRepository: MateriaRepository) {

    fun execute(usuarioId: String, callback: (List<Materia>?, String?) -> Unit) {
        materiaRepository.buscarMaterias(usuarioId) { materiasMap, mensagemErro ->
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
                callback(null, mensagemErro)
            }
        }
    }
}