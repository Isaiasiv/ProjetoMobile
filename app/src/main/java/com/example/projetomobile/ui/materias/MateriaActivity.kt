package com.example.projetomobile.ui.materias

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.example.projetomobile.data.MateriaRepository
import com.example.projetomobile.domain.CriarMateriaUseCase
import com.example.projetomobile.domain.Materia

class MateriaActivity : AppCompatActivity() {

    private val criarMateriaUseCase by lazy {
        CriarMateriaUseCase(MateriaRepository())
    }
    private lateinit var materiaEditText: EditText
    private lateinit var buttonConfirmar: Button
    private lateinit var buttonCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarComponentes()
    }
    private fun iniciarComponentes() {
        materiaEditText = findViewById(R.id.editTextDialogo)
        buttonCancelar = findViewById(R.id.buttonCancelar)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)

        buttonConfirmar.isEnabled = false

        // Monitora alterações no EditText para habilitar o botão dinamicamente
        materiaEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buttonConfirmar.isEnabled = !s.isNullOrBlank()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonCancelar.setOnClickListener {
            finish()
        }

        buttonConfirmar.setOnClickListener {
            val nomeMateria = materiaEditText.text.toString()
            val materia = Materia(nome = nomeMateria, descricao = "Descrição padrão")
            criarMateria(materia)
        }
    }

    private fun criarMateria(materia: Materia) {
        criarMateriaUseCase.execute(materia) { sucesso, mensagem ->
            if (sucesso) {
                Toast.makeText(this, "Matéria criada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao criar matéria: $mensagem", Toast.LENGTH_SHORT).show()
            }
        }
    }
}