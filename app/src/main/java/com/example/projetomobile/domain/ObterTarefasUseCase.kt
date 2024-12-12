package com.example.projetomobile.domain

import com.example.projetomobile.data.TarefaRepository
import com.example.projetomobile.domain.Tarefa

class ObterTarefasUseCase(private val tarefaRepository: TarefaRepository) {

    fun execute(usuarioId: String, materiaId: String, callback: (List<Tarefa>?, String?) -> Unit) {
        tarefaRepository.buscarTarefas(usuarioId, materiaId) { tarefasMap, mensagemErro ->
            if (tarefasMap != null) {
                val tarefas = tarefasMap.map {
                    Tarefa(
                        //id = it["id"] as? String ?: "",
                        nome = it["nome"] as? String ?: "",
                        titulo = it["titulo"] as? String ?: "",
                        objetivo = it["objetivo"] as? String ?: "",
                        descricao = it["descricao"] as? String ?: "",
                        dataInicio = it["dataInicio"] as? String ?: "",
                        dataFim = it["dataFim"] as? String ?: "",
                        status = it["status"] as? String ?: ""
                    )
                }
                callback(tarefas, null)
            } else {
                callback(null, mensagemErro)
            }
        }
    }
}