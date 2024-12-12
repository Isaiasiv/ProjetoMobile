package com.example.projetomobile.ui.home.ui.menuInferior

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa
import com.example.projetomobile.ui.home.ui.home.HomeAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HomePrincipalFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private val tarefas = mutableListOf<Tarefa>()
    private var lastVisible: DocumentSnapshot? = null
    private var isLoading = false
    private val pageSize = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_principal, container, false)
        recyclerView = view.findViewById(R.id.recycler_feed)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeAdapter = HomeAdapter(tarefas)
        recyclerView.adapter = homeAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    carregarTarefas()
                }
            }
        })

        carregarTarefas()

        return view
    }

    private fun carregarTarefas() {
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        isLoading = true

        val db = FirebaseFirestore.getInstance()
        var query: Query = db.collection("Usuarios").document(usuarioId)
            .collection("tarefas")
            .orderBy("nome")
            .limit(pageSize.toLong())

        lastVisible?.let {
            query = query.startAfter(it)
        }

        query.get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    lastVisible = snapshot.documents[snapshot.size() - 1]
                    val novasTarefas = snapshot.toObjects(Tarefa::class.java)
                    tarefas.addAll(novasTarefas)
                    homeAdapter.notifyDataSetChanged()
                }
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }




    }
}
