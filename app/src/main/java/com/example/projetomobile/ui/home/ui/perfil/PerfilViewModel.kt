package com.example.projetomobile.ui.home.ui.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetomobile.domain.ObterUsuarioUseCase
import com.example.projetomobile.domain.Usuario


class PerfilViewModel(private val obterUsuarioUseCase: ObterUsuarioUseCase) : ViewModel() {

    private val _usuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = _usuario

    private val _erro = MutableLiveData<String>()
    val erro: LiveData<String> = _erro

    fun carregarUsuario(usuarioID: String) {
        obterUsuarioUseCase.executar(usuarioID) { usuario, erro ->
            if (usuario != null) {
                _usuario.postValue(usuario)
            } else {
                _erro.postValue(erro)
            }
        }
    }
}