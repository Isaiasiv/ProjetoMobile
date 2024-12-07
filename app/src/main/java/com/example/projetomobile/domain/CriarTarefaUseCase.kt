package com.example.projetomobile.domain

import com.example.projetomobile.data.TarefaRepository

class CriarTarefaUseCase(private val repository: TarefaRepository) {
    fun execute(

        materiaId: String,
        tarefa: Tarefa,
        callback: (Boolean, String?) -> Unit
    ) {
        repository.criarTarefa(materiaId, tarefa, callback)
    }
}
