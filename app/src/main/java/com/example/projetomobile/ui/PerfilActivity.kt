package com.example.projetomobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projetomobile.R
import com.example.projetomobile.domain.Usuario
import com.example.projetomobile.ui.login.Login
import com.example.projetomobile.ui.home.ui.perfil.PerfilViewModel
import com.example.projetomobile.ui.home.ui.perfil.PerfilViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {

    private lateinit var nomeTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var buttonDeslogar: Button

    private val viewModel: PerfilViewModel by viewModels {
        PerfilViewModelFactory() // Implementar um ViewModelProvider.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_perfil)
        iniciarComponents()

        observarViewModel()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val usuarioID = currentUser?.uid ?: return
        viewModel.carregarUsuario(usuarioID)

        buttonDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun iniciarComponents() {
        nomeTextView = findViewById(R.id.textViewUsuario)
        emailTextView = findViewById(R.id.textViewEmail)
        buttonDeslogar = findViewById(R.id.buttonSairDaConta)
    }

    private fun observarViewModel() {
        viewModel.usuario.observe(this) { usuario ->
            atualizarUI(usuario)
        }

        viewModel.erro.observe(this) { mensagemErro ->
            Toast.makeText(this, mensagemErro, Toast.LENGTH_LONG).show()
        }
    }

    private fun atualizarUI(usuario: Usuario) {
        nomeTextView.text = usuario.nome
        emailTextView.text = usuario.email
    }
}
