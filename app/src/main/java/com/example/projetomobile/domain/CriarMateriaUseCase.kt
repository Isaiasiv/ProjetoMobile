package com.example.projetomobile.domain

import com.example.projetomobile.data.MateriaRepository

class CriarMateriaUseCase(private val materiaRepository: MateriaRepository) {

    fun execute(materia: Materia, callback: (Boolean, String?) -> Unit) {
        materiaRepository.criarMateria(
            materia.nome,
            materia.descricao
        ) { sucesso, mensagem ->
            callback(sucesso, mensagem)
        }
    }
}
