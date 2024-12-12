package com.example.projetomobile.ui.tarefas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.data.TarefaRepository
import com.example.projetomobile.domain.Materia
import com.example.projetomobile.domain.ObterTarefasUseCase
import com.example.projetomobile.domain.Tarefa
import com.example.projetomobile.ui.materias.AddTarefaActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class TarefasViewActivity : AppCompatActivity() {

    private lateinit var tarefaAdapter: TarefaAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var materiaId: String
    private val tarefaRepository = TarefaRepository() // Adiciona a instância do repositório
    private val tarefas: MutableList<Tarefa> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_view)

        // Recupera o ID da matéria passado pela Intent
        materiaId = intent.getStringExtra("materia_id") ?: throw IllegalArgumentException("materia_id não foi passado")

        recyclerView = findViewById(R.id.recycler_view_main)
        tarefaAdapter = TarefaAdapter(mutableListOf())
        recyclerView.adapter = tarefaAdapter

        // Buscar as tarefas no Firestore
        buscarTarefas()

        // Configurando o ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(tarefaAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Configurando o clique no FloatingActionButton
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddTarefaActivity::class.java)
            intent.putExtra("materia_id", materiaId)  // Passa o ID da matéria
            startActivityForResult(intent, REQUEST_CODE_ADD_TAREFA)
        }

        tarefaAdapter.setOnItemClickListener { tarefa ->
            val intent = Intent(this, TarefaActivity::class.java)
            intent.putExtra("materia_id", materiaId) // Passando o ID da matéria
            intent.putExtra("tarefa_id", tarefa.id) // Passando o ID da tarefa
            intent.putExtra("tarefa_nome", tarefa.nome)
            intent.putExtra("tarefa_titulo", tarefa.titulo)
            intent.putExtra("tarefa_status", tarefa.status)
            intent.putExtra("tarefa_descricao", tarefa.descricao)
            intent.putExtra("tarefa_objetivo", tarefa.objetivo)
            intent.putExtra("tarefa_dataFim", tarefa.dataFim)
            intent.putExtra("tarefa_dataInicio", tarefa.dataInicio)
            startActivity(intent)
        }




    }
    companion object {
        private const val REQUEST_CODE_ADD_TAREFA = 1001
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TAREFA && resultCode == RESULT_OK) {
            // Recarrega as tarefas
            buscarTarefas()
        }
    }



    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
    private fun buscarTarefas() {
        val usuarioId = usuarioId // Assuma que esta variável contém o ID do usuário
        val materiaId = this.materiaId // ID da matéria associado às tarefas

        if (usuarioId != null && materiaId != null) {
            val repository = TarefaRepository()
            val obterTarefasUseCase = ObterTarefasUseCase(repository)

            // Passando os IDs necessários para o caso de uso
            obterTarefasUseCase.execute(usuarioId, materiaId) { tarefasList, mensagemErro ->
                if (tarefasList != null) {
                    // Atualiza o adapter diretamente
                    tarefaAdapter.updateTarefas(tarefasList)
                } else {
                    mensagemErro?.let {
                        Log.e("TarefasViewActivity", "Erro ao buscar tarefas: $it")
                    }
                }
            }

        } else {
            // Caso o usuarioId ou materiaId seja nulo
            Log.e("TarefasViewActivity", "Dados insuficientes para buscar tarefas. Usuário ou matéria inválido.")
        }
    }


}
