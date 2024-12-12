package com.example.projetomobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.example.projetomobile.domain.Verificacoes
import com.example.projetomobile.ui.PerfilActivity
import com.example.projetomobile.ui.cadastro.Cadastro
import com.example.projetomobile.ui.home.HomeActivity
import com.example.projetomobile.ui.materias.AddTarefaActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var textTelaCadastro: TextView
    private lateinit var EditTextEmail: EditText
    private lateinit var EditTextPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonEntrar: Button
    private lateinit var buttonrecuperarSenha: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciarComponents()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val verificacoes = Verificacoes()

        buttonrecuperarSenha.setOnClickListener{

            verificacoes.verificarData("16/12/2024", this, "Atividade de matematica")

        }
        buttonEntrar.setOnClickListener {
            val email = EditTextEmail.text.toString()
            val senha = EditTextPassword.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Informe um email válido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                        // Tornar o ProgressBar visível
                        progressBar.visibility = android.view.View.VISIBLE

                        // Usar Handler para atraso
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            TelaPrincipal()
                            //ButtonFutuant()
                        }, 3000) // 3 segundos de atraso
                    } else {
                        Toast.makeText(this, "Erro ao realizar login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        configurarCliqueCadastro()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            TelaPrincipal()
            //ButtonFutuant()
        }
    }

    private fun TelaPrincipal() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun ButtonFutuant() {
        val intent = Intent(this, AddTarefaActivity::class.java)
        startActivity(intent)
        //finish()
    }

    private fun iniciarComponents() {
        textTelaCadastro = findViewById(R.id.textCadastro)
        EditTextEmail = findViewById(R.id.editTextLogin)
        EditTextPassword = findViewById(R.id.editTextPassword)
        buttonEntrar = findViewById(R.id.buttonEntrar)
        buttonrecuperarSenha = findViewById(R.id.recuperarSenha)
        progressBar = findViewById(R.id.progressbar)
    }

    private fun configurarCliqueCadastro() {
        textTelaCadastro.setOnClickListener {
            startActivity(Intent(this, Cadastro::class.java))
        }
    }
}
