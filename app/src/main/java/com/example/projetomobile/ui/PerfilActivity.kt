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
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {

    private lateinit var nomeTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var buttonDeslogar: Button

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var usuarioID: String


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

        val currentUser = FirebaseAuth.getInstance().currentUser
        val email = currentUser?.email
        usuarioID = currentUser?.uid ?: return // Retorna se o usuário for nulo

        val documentReference = db.collection("Usuarios").document(usuarioID)
        documentReference.addSnapshotListener { documentSnapshot, error ->
            if (error != null) {
                // Lidar com o erro, se necessário
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                nomeTextView.text = documentSnapshot.getString("Usuario")
                emailTextView.text = email
            }
        }
    }

    private fun iniciarComponents() {

        nomeTextView = findViewById(R.id.textViewUsuario)
        emailTextView = findViewById(R.id.textViewEmail)
        buttonDeslogar = findViewById(R.id.buttonSairDaConta)
    }
}