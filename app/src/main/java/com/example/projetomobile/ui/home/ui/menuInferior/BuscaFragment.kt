package com.example.projetomobile.ui.home.ui.menuInferior

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa
import com.google.firebase.firestore.FirebaseFirestore

class BuscaFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: BuscaAdapter
    private val firestore = FirebaseFirestore.getInstance()

    private var usuarioId: String? = null
    private var materiaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuarioId = it.getString(ARG_USUARIO_ID)
            materiaId = it.getString(ARG_MATERIA_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_busca, container, false)

        recyclerView = view.findViewById(R.id.recycler_feed)
        searchView = view.findViewById(R.id.searchView)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BuscaAdapter(mutableListOf())
        recyclerView.adapter = adapter

        fetchData()
        setupSearch()

        return view
    }

    private fun fetchData() {
        if (usuarioId == null || materiaId == null) return

        progressBar.visibility = View.VISIBLE
        val tarefaCollectionPath = "Usuarios/QgGVDaK1W3gGZzYxwnHmsw7x1iv1/materias/9G6y9UtGduPxyWq8DbOy/tarefas"

        firestore.collection(tarefaCollectionPath).get()
            .addOnSuccessListener { result ->
                val tarefas = result.documents.mapNotNull { doc ->
                    doc.toObject(Tarefa::class.java)?.apply { id = doc.id }
                }
                adapter.updateData(tarefas)
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
            }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.filterData(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filterData(it) }
                return true
            }
        })
    }

    companion object {
        private const val ARG_USUARIO_ID = "usuarioId"
        private const val ARG_MATERIA_ID = "materiaId"

        @JvmStatic
        fun newInstance(usuarioId: String, materiaId: String) =
            BuscaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USUARIO_ID, usuarioId)
                    putString(ARG_MATERIA_ID, materiaId)
                }
            }
    }
}
