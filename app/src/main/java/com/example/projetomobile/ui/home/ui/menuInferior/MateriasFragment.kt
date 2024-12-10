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
import com.example.projetomobile.ui.materias.AddTarefaActivity
import com.example.projetomobile.ui.materias.MateriaAdapter
import com.example.projetomobile.ui.tarefas.TarefasViewActivity
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MateriasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MateriasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MateriaAdapter
    private val materias: MutableList<Materia> = mutableListOf() // Lista mutável para atualizar dinamicamente

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Configura o RecyclerView
        recyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        // Obtenha a lista de materias
        materias = ObterMateriasUseCase.getMaterias()


        recyclerView.adapter = adapter
    }*/override fun onCreateView(
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

        adapter = MateriaAdapter(materias) { materia ->
            // Clique em uma matéria
            exibirDetalhesMateria(materia)
        }

        recyclerView.adapter = adapter

        buscarMaterias()
    }

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materias, container, false)

    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MateriasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MateriasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private val usuarioId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid
    private fun buscarMaterias() {

        val usuarioId = usuarioId
        if (usuarioId != null) {
            val repository = MateriaRepository()
            val obterMateriasUseCase = ObterMateriasUseCase(repository)

            // Passando o usuarioId para o execute
            obterMateriasUseCase.execute(usuarioId) { materiasList, mensagemErro ->
                if (materiasList != null) {
                    materias.clear()
                    materias.addAll(materiasList)
                    adapter.notifyDataSetChanged() // Atualiza o RecyclerView
                } else {
                    // Trate o erro, como exibir uma mensagem
                    mensagemErro?.let {
                        // Exibe um Toast ou Log para depuração
                        Log.e("MateriasFragment", "Erro ao buscar matérias: $it")
                    }
                }
            }
        } else {
            // Caso o usuarioId seja nulo (usuário não autenticado)
            Log.e("MateriasFragment", "Usuário não autenticado.")
        }
    }

    private fun exibirDetalhesMateria(materia: Materia) {
        val intent = Intent(requireContext(), TarefasViewActivity::class.java)
        // Passar o ID da matéria para a nova Activity
        intent.putExtra("materia_id", materia.id)  // Aqui estamos passando o ID
        // Iniciar a nova Activity
        startActivity(intent)
    }

}