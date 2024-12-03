package com.example.projetomobile.ui.home.ui.perfil

import UsuarioRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projetomobile.domain.ObterUsuarioUseCase

class PerfilViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            val repository = UsuarioRepository() // Instância do repositório
            val useCase = ObterUsuarioUseCase(repository) // Instância do caso de uso
            return PerfilViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
