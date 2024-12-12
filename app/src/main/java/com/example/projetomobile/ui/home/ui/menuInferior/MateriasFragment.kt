package com.example.projetomobile.ui.home.ui.menuInferior
import ObterMateriasUseCase
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.data.MateriaRepository
import com.example.projetomobile.domain.Materia
import com.example.projetomobile.ui.materias.MateriaAdapter
import com.example.projetomobile.ui.tarefas.TarefasViewActivity
import com.google.firebase.auth.FirebaseAuth

class MateriasFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MateriaAdapter
    private val materias: MutableList<Materia> = mutableListOf() // Lista mutável para atualizar dinamicamente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_feed)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        adapter = MateriaAdapter(materias, { materia ->
            // Clique em uma matéria
            exibirDetalhesMateria(materia)
        }) {
            buscarMaterias() // Atualiza a lista de matérias ao excluir
        }

        recyclerView.adapter = adapter

        buscarMaterias()
    }



    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    private fun buscarMaterias() {
        val usuarioId = usuarioId
        if (usuarioId != null) {
            val repository = MateriaRepository()
            val obterMateriasUseCase = ObterMateriasUseCase(repository)

            obterMateriasUseCase.execute(usuarioId) { materiasList, mensagemErro ->
                if (materiasList != null) {
                    materias.clear()
                    materias.addAll(materiasList)
                    adapter.notifyDataSetChanged() // Atualiza o RecyclerView
                } else {
                    mensagemErro?.let {
                        Log.e("MateriasFragment", "Erro ao buscar matérias: $it")
                    }
                }
            }
        } else {
            Log.e("MateriasFragment", "Usuário não autenticado.")
        }
    }

    private fun exibirDetalhesMateria(materia: Materia) {
        val intent = Intent(requireContext(), TarefasViewActivity::class.java)
        intent.putExtra("materia_id", materia.id)
        startActivity(intent)
    }
}
