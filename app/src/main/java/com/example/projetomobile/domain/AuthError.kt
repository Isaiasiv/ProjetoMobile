package com.example.projetomobile.domain

sealed class AuthError(val message: String) {
    object WeakPassword : AuthError("A senha deve ter pelo menos 6 caracteres.")
    object InvalidEmail : AuthError("O e-mail fornecido é inválido.")
    object EmailAlreadyInUse : AuthError("O e-mail já está em uso. Tente outro.")
    class UnknownError(message: String) : AuthError(message)
}
