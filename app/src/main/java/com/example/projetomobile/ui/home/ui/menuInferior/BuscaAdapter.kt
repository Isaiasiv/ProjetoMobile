package com.example.projetomobile.ui.home.ui.menuInferior

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa

class BuscaAdapter(private var tarefas: MutableList<Tarefa>) :
    RecyclerView.Adapter<BuscaAdapter.ViewHolder>() {

    private var tarefasFiltradas: MutableList<Tarefa> = tarefas.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarefa = tarefasFiltradas[position]
        holder.bind(tarefa)
    }

    override fun getItemCount(): Int = tarefasFiltradas.size

    fun updateData(newTarefas: List<Tarefa>) {
        tarefas.clear()
        tarefas.addAll(newTarefas)
        tarefasFiltradas = tarefas.toMutableList()
        notifyDataSetChanged()
    }

    fun filterData(query: String) {
        tarefasFiltradas = if (query.isEmpty()) {
            tarefas.toMutableList()
        } else {
            tarefas.filter { it.titulo.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.textViewTarefaTitulo)
        private val objetivo: TextView = itemView.findViewById(R.id.textViewObjetivo)
        private val status: TextView = itemView.findViewById(R.id.textViewStatus)
        private val materia: TextView = itemView.findViewById(R.id.textViewMateria)
        private val image: ImageView = itemView.findViewById(R.id.image_language)
        private val button: Button = itemView.findViewById(R.id.button_with_icon)

        fun bind(tarefa: Tarefa) {
            titulo.text = tarefa.titulo
            objetivo.text = tarefa.objetivo
            status.text = tarefa.status
            materia.text = tarefa.dataFim
            // Configure o botão ou outros eventos se necessário
        }
    }
}
