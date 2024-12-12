package com.example.projetomobile.ui.tarefas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TarefaActivity : AppCompatActivity() {
    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    private lateinit var materiaId: String
    private lateinit var tarefaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperando os componentes do layout
        val textTitulo: TextView = findViewById(R.id.textViewTarefaTitulo)
        val textNome: TextView = findViewById(R.id.textViewTarefaNomeDaTarefa)
        val textVencimento: TextView = findViewById(R.id.textViewTarefaDataFim)
        val textDataInicia: TextView = findViewById(R.id.textViewTarefaDataInicial)
        val textObjetivo: TextView = findViewById(R.id.textViewTarefaObjetivo)
        val textDescricao: TextView = findViewById(R.id.textViewTarefaDetalhes)
        val btnDeletar: Button = findViewById(R.id.buttonDeletar)

        // Recuperar IDs
        materiaId = intent.getStringExtra("materia_id") ?: "ID da matéria não encontrado"
        tarefaId = intent.getStringExtra("tarefa_id") ?: "ID da tarefa não encontrado"

        // Recuperar os dados do Intent
        val tarefaNome = intent.getStringExtra("tarefa_nome")
        val tarefaTitulo = intent.getStringExtra("tarefa_titulo")
        val tarefaStatus = intent.getStringExtra("tarefa_status")
        val tarefaDescricao = intent.getStringExtra("tarefa_descricao")
        val tarefaObjetivo = intent.getStringExtra("tarefa_objetivo")
        val tarefaDataFim = intent.getStringExtra("tarefa_dataFim")
        val tarefaDataInicio = intent.getStringExtra("tarefa_dataInicio")

        // Atribuindo os valores aos componentes
        textTitulo.text = tarefaNome
        textNome.text = tarefaTitulo
        textVencimento.text = tarefaDataFim
        textDataInicia.text = tarefaDataInicio
        textObjetivo.text = tarefaObjetivo
        textDescricao.text = tarefaDescricao

        // Referências aos botões de editar
        val btnEditar6: ImageButton = findViewById(R.id.btnEditar6)
        val btnEditar5: ImageButton = findViewById(R.id.btnEditar5)
        val btnEditar3: ImageButton = findViewById(R.id.btnEditar3)
        val btnEditar4: ImageButton = findViewById(R.id.btnEditar4)
        val btnEditar: ImageButton = findViewById(R.id.btnEditar)
        val btnEditar2: ImageButton = findViewById(R.id.btnEditar2)

        // Configuração dos eventos de clique para cada botão
        btnEditar6.setOnClickListener { showEditDialog("Título da Matéria", textTitulo) }
        btnEditar5.setOnClickListener { showEditDialog("Data Inicial da Matéria", textDataInicia) }
        btnEditar3.setOnClickListener { showEditDialog("Nome da Matéria", textNome) }
        btnEditar4.setOnClickListener { showEditDialog("Data Final da Matéria", textVencimento) }
        btnEditar.setOnClickListener { showEditDialog("Objetivo da Matéria", textObjetivo) }
        btnEditar2.setOnClickListener { showEditDialog("Descrição da Matéria", textDescricao) }

        btnDeletar.setOnClickListener {
            // Lógica para deletar a tarefa
        }
    }

    private fun showEditDialog(field: String, textView: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar $field")

        val input = EditText(this)
        input.setText(textView.text)
        builder.setView(input)

        builder.setPositiveButton("Salvar") { dialog, _ ->
            val newValue = input.text.toString()
            if (newValue.isNotEmpty()) {
                updateFirestore(field, newValue)
                textView.text = newValue
                Toast.makeText(this, "$field atualizado para $newValue", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateFirestore(field: String, newValue: String) {
        usuarioId?.let { id ->
            val db = FirebaseFirestore.getInstance()
            val documentRef = db.collection("Usuarios").document(id)
                .collection("materias").document(materiaId)
                .collection("tarefas").document(tarefaId)

            val updates = when (field) {
                "Título da Matéria" -> mapOf("titulo" to newValue)
                "Data Inicial da Matéria" -> mapOf("dataInicio" to newValue)
                "Nome da Matéria" -> mapOf("nome" to newValue)
                "Data Final da Matéria" -> mapOf("dataFim" to newValue)
                "Objetivo da Matéria" -> mapOf("objetivo" to newValue)
                "Descrição da Matéria" -> mapOf("descricao" to newValue)
                else -> return
            }

            documentRef.update(updates)
                .addOnSuccessListener {
                    Log.d("Firestore", "$field atualizado com sucesso!")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Erro ao atualizar $field", e)
                }
        }
    }
}
