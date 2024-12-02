package com.example.projetomobile.domain

class CadastroValidator {

    fun validarCadastro(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        termosPrivacidade: Boolean,
        termosUso: Boolean
    ): List<String> {
        val erros = mutableListOf<String>()

        if (nome.isBlank()) erros.add("O campo Nome é obrigatório.")
        if (email.isBlank()) erros.add("O campo Email é obrigatório.")
        if (!email.contains("@")) erros.add("Informe um Email válido.")
        if (senha.isBlank()) erros.add("O campo Senha é obrigatório.")
        if (confirmarSenha.isBlank()) erros.add("O campo Confirmar Senha é obrigatório.")
        if (senha != confirmarSenha) erros.add("As senhas não conferem.")
        if (!termosPrivacidade) erros.add("Aceite os Termos de Privacidade.")
        if (!termosUso) erros.add("Aceite os Termos de Uso.")

        return erros
    }

}
