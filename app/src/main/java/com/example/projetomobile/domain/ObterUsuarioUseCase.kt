package com.example.projetomobile.domain

import UsuarioRepository


class ObterUsuarioUseCase(private val repository: UsuarioRepository) {

    fun executar(usuarioID: String, callback: (Usuario?, String?) -> Unit) {
        repository.obterUsuario(usuarioID, callback)
    }
}
