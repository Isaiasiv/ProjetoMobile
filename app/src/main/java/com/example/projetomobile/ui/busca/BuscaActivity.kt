package com.example.projetomobile.ui.busca

import TarefasAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetomobile.data.TarefaRepository
import com.example.projetomobile.databinding.FragmentBuscaBinding


class BuscaActivity : AppCompatActivity() {
/*
    private lateinit var binding: FragmentBuscaBinding
    private lateinit var tarefasAdapter: TarefasAdapter
    private val tarefaRepository = TarefaRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBuscaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        setupSearchView()
        carregarTarefas()
    }

    private fun initRecyclerView() {
        tarefasAdapter = TarefasAdapter()
        binding.recyclerFeed.apply {
            layoutManager = LinearLayoutManager(this@BuscaActivity)
            adapter = tarefasAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filtrarTarefas(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filtrarTarefas(it) }
                return true
            }
        })
    }

    private fun carregarTarefas() {
        val usuarioId = "usuario_id_exemplo" // Substitua pelo ID do usuário
        val materiaId = "materia_id_exemplo" // Substitua pelo ID da matéria

        binding.progressBar.visibility = android.view.View.VISIBLE
        tarefaRepository.buscarTarefas(usuarioId, materiaId) { tarefas, erro ->
            binding.progressBar.visibility = android.view.View.GONE
            if (tarefas != null) {
                tarefasAdapter.updateTarefas(tarefas)
            } else {
                Log.e("BuscaActivity", "Erro ao buscar tarefas: $erro")
            }
        }
    }

    private fun filtrarTarefas(query: String) {
        val tarefasFiltradas = tarefasAdapter.tarefas.filter { tarefa ->
            (tarefa["titulo"] as? String)?.contains(query, ignoreCase = true) == true
        }
        tarefasAdapter.updateTarefas(tarefasFiltradas)
    }*/
}
