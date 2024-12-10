package com.example.projetomobile.ui.tarefas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.ui.materias.AddTarefaActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
class TarefasViewActivity : AppCompatActivity() {

    private lateinit var tarefaAdapter: TarefaAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var materiaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_view)

        // Recupera o ID da matéria passado pela Intent
        materiaId = intent.getStringExtra("materia_id") ?: throw IllegalArgumentException("materia_id não foi passado")

        recyclerView = findViewById(R.id.recycler_view_main)
        tarefaAdapter = TarefaAdapter(fakeEmails())
        recyclerView.adapter = tarefaAdapter

        // Configurando o ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(tarefaAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Configurando o clique no FloatingActionButton
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Passa o ID da matéria para AddTarefaActivity
            val intent = Intent(this, AddTarefaActivity::class.java)
            intent.putExtra("materia_id", materiaId)  // Passa o ID da matéria
            startActivity(intent)
        }
    }
}

