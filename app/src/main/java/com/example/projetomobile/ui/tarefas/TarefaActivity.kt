package com.example.projetomobile.ui.tarefas

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R

class TarefaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tarefa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Recuperando os componentes do layout
        val textTitulo: TextView = findViewById(R.id.textView13)
        val textNome: TextView = findViewById(R.id.textView3)
        val textVencimento: TextView = findViewById(R.id.textView9)
        val textStatus: TextView = findViewById(R.id.textView10)
        val textObjetivo: TextView = findViewById(R.id.textView11)
        val textDescricao: TextView = findViewById(R.id.textView12)
        val textDatas: TextView = findViewById(R.id.textView14)
        val btnEditar: ImageButton = findViewById(R.id.btnEditar)
        val btnDeletar: Button = findViewById(R.id.button3)

        // Recuperar IDs
        val materiaId = intent.getStringExtra("materia_id") ?: "ID da matéria não encontrado"
        val tarefaId = intent.getStringExtra("tarefa_id") ?: "ID da tarefa não encontrado"
        // Recuperar os dados do Intent
        val tarefaNome = intent.getStringExtra("tarefa_nome")
        val tarefaTitulo = intent.getStringExtra("tarefa_titulo")
        val tarefaStatus = intent.getStringExtra("tarefa_status")
        val tarefaDescricao = intent.getStringExtra("tarefa_descricao")
        val tarefaObjetivo = intent.getStringExtra("tarefa_objetivo")
        val tarefaDataFim = intent.getStringExtra("tarefa_dataFim")
        val tarefaDataInicio = intent.getStringExtra("tarefa_dataInicio")

        // Atribuindo os valores aos componentes
        textTitulo.text = tarefaTitulo
        textNome.text = tarefaNome
        textVencimento.text = "Vencimento: $tarefaDataFim"
        textStatus.text = tarefaStatus
        textObjetivo.text = tarefaObjetivo
        textDescricao.text = tarefaDescricao
        //textDatas.text = "Início: $dataInicio\nFim: $dataFim"

        //textViewMateriaId.text = "Matéria ID: $materiaId"
        //textViewTarefaId.text = "Tarefa ID: $tarefaId"
        btnEditar.setOnClickListener {
            // Lógica para editar a tarefa
        }

        btnDeletar.setOnClickListener {
            // Lógica para deletar a tarefa
        }
    }
}