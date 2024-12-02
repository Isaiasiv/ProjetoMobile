package com.example.projetomobile.data

import com.example.projetomobile.domain.AuthError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class FirebaseAuthService {

    fun cadastrarUsuario(
        email: String,
        senha: String,
        callback: (Boolean, AuthError?) -> Unit
    ) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    val erro = when (val exception = task.exception) {
                        is FirebaseAuthWeakPasswordException -> AuthError.WeakPassword
                        is FirebaseAuthInvalidCredentialsException -> AuthError.InvalidEmail
                        is FirebaseAuthUserCollisionException -> AuthError.EmailAlreadyInUse
                        else -> AuthError.UnknownError(exception?.message ?: "Erro desconhecido.")
                    }
                    callback(false, erro)
                }
            }
    }
}
