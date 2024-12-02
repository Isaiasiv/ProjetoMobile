package com.example.projetomobile.ui.cadastro

import SalvarUsuarioUseCase
import UsuarioRepository
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetomobile.R
import com.example.projetomobile.domain.CadastroValidator
import com.example.projetomobile.data.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth

class Cadastro : AppCompatActivity() {

    private lateinit var textEditNome: EditText
    private lateinit var textEditEmail: EditText
    private lateinit var textEditSenha: EditText
    private lateinit var textEditConfirmarSenha: EditText
    private lateinit var radioButtonTermosPrivacidade: RadioButton
    private lateinit var radioButtonTemosDeUso: RadioButton
    private lateinit var buttonCadastrar: Button

    private val usuarioRepository = UsuarioRepository()
    private val salvarUsuarioUseCase = SalvarUsuarioUseCase(usuarioRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        iniciarComponentes()

        // Clique no botão cadastrar
        buttonCadastrar.setOnClickListener {
            if (validarCampos()) {
                cadastrarUsuario()
            }
        }
    }

    private fun iniciarComponentes() {
        textEditNome = findViewById(R.id.editTextNome)
        textEditEmail = findViewById(R.id.editTextEmail)
        textEditSenha = findViewById(R.id.editTextSenha)
        textEditConfirmarSenha = findViewById(R.id.editTextSenhaConfirme)
        radioButtonTermosPrivacidade = findViewById(R.id.radioButtonTermosPrivacidade)
        radioButtonTemosDeUso = findViewById(R.id.radioButtonTemosDeUso)
        buttonCadastrar = findViewById(R.id.buttonCadastrar)

        buttonCadastrar.isEnabled = false

        // Listeners para habilitar o botão
        radioButtonTemosDeUso.setOnCheckedChangeListener { _, _ -> verificarSelecao() }
        radioButtonTermosPrivacidade.setOnCheckedChangeListener { _, _ -> verificarSelecao() }
    }

    private fun verificarSelecao() {
        buttonCadastrar.isEnabled =
            radioButtonTemosDeUso.isChecked && radioButtonTermosPrivacidade.isChecked
    }

    private fun validarCampos(): Boolean {
        val validator = CadastroValidator()
        val nome = textEditNome.text.toString()
        val email = textEditEmail.text.toString()
        val senha = textEditSenha.text.toString()
        val confirmarSenha = textEditConfirmarSenha.text.toString()

        val erros = validator.validarCadastro(
            nome, email, senha, confirmarSenha,
            radioButtonTermosPrivacidade.isChecked,
            radioButtonTemosDeUso.isChecked
        )

        if (erros.isNotEmpty()) {
            Toast.makeText(this, erros.joinToString("\n"), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun cadastrarUsuario() {
        val email = textEditEmail.text.toString()
        val senha = textEditSenha.text.toString()

        val authService = FirebaseAuthService()
        authService.cadastrarUsuario(email, senha) { sucesso, erro ->
            if (sucesso) {
                salvarDadosDoUsuario()
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Aqui você pode redirecionar para outra tela
            } else {
                Toast.makeText(this, erro?.message ?: "Erro ao cadastrar o usuário.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun salvarDadosDoUsuario() {
        val email = textEditEmail.text.toString()
        val nomeUsuario = textEditNome.text.toString()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        if (usuarioID != null) {
            salvarUsuarioUseCase.execute(usuarioID, nomeUsuario, email) { sucesso, erro ->
                if (sucesso) {
                    Log.d("db", "Sucesso ao salvar os dados no BD")
                } else {
                    Log.d("db_error", "Erro ao salvar os dados no BD: $erro")
                }
            }
        } else {
            Log.d("db_error", "Usuário não autenticado. Não foi possível salvar os dados.")
        }
    }
}
