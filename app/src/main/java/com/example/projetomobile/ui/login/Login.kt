package com.example.projetomobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.example.projetomobile.ui.cadastro.Cadastro

class Login : AppCompatActivity() {

    private lateinit var textTelaCadastro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        // Configurar margens para as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar os componentes
        iniciarComponents()

        // Configurar o clique no TextView
        configurarCliqueCadastro()
    }

    // Inicializar os componentes
    private fun iniciarComponents() {
        textTelaCadastro = findViewById(R.id.textCadastro)
    }

    // Configurar ação para o clique do TextView
    private fun configurarCliqueCadastro() {
        textTelaCadastro.setOnClickListener {
            val intent = Intent(this@Login, Cadastro::class.java)
            startActivity(intent)
        }
    }
}
