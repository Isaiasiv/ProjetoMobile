package com.example.projetomobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.example.projetomobile.ui.login.Login
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {

    private lateinit var nomeTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var buttonDeslogar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarComponents()

        //desloga usuario
        buttonDeslogar.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun iniciarComponents() {

        nomeTextView = findViewById(R.id.textViewUsuario)
        emailTextView = findViewById(R.id.textViewEmail)
        buttonDeslogar = findViewById(R.id.buttonSairDaConta)
    }
}