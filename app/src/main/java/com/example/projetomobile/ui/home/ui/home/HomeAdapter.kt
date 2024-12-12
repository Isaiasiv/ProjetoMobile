package com.example.projetomobile.ui.home.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa

class HomeAdapter(private var tarefas: MutableList<Tarefa>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_materia, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.bind(tarefa)
    }

    override fun getItemCount(): Int = tarefas.size

    fun updateTarefas(novasTarefas: List<Tarefa>) {
        tarefas.addAll(novasTarefas)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTarefaTitulo)
        private val textViewObjetivo: TextView = itemView.findViewById(R.id.textViewObjetivo)

        fun bind(tarefa: Tarefa) {
            textViewTitulo.text = tarefa.nome
            textViewObjetivo.text = tarefa.objetivo
        }
    }
}
