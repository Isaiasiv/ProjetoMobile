package com.example.projetomobile.ui.tarefas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa

class TarefaAdapter(private var tarefas: MutableList<Tarefa>) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var onItemClickListener: ((Tarefa) -> Unit)? = null

    fun setOnItemClickListener(listener: (Tarefa) -> Unit) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.bind(tarefa)

        // Adiciona o clique no item
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(tarefa)
        }
    }

    override fun getItemCount(): Int = tarefas.size

    fun updateTarefas(novasTarefas: List<Tarefa>) {
        tarefas.clear()
        tarefas.addAll(novasTarefas)
        notifyDataSetChanged()
    }

    class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome: TextView = itemView.findViewById(R.id.txtNome)
        private val textViewObjetivo: TextView = itemView.findViewById(R.id.textViewObjetivo)
        private val textViewDataFinal: TextView = itemView.findViewById(R.id.txt_date)

        fun bind(tarefa: Tarefa) {
            textViewNome.text = tarefa.nome
            textViewObjetivo.text = tarefa.objetivo
            textViewDataFinal.text = tarefa.dataFim
        }
    }
}
