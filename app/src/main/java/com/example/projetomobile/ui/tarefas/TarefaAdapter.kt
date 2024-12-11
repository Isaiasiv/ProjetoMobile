package com.example.projetomobile.ui.tarefas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Tarefa

class TarefaAdapter(private val tarefas: MutableList<Tarefa>) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloText: TextView = itemView.findViewById(R.id.txt_user)
        val descricaoText: TextView = itemView.findViewById(R.id.txt_subject)
        //val statusText: TextView = itemView.findViewById(R.id.txt_status)
        //val starIcon: ImageView = itemView.findViewById(R.id.img_star)

        fun bind(tarefa: Tarefa) {
            tituloText.text = tarefa.titulo
            descricaoText.text = tarefa.descricao
            //starIcon.setImageResource(if (tarefa.stared) R.drawable.ic_coracao_cheio else R.drawable.ic_coracao)

            // Clique no item para abrir nova atividade
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, TarefaActivity::class.java)

                // Passando dados para a nova atividade
                intent.putExtra("TITULO", tarefa.titulo)
                intent.putExtra("DESCRICAO", tarefa.descricao)
                //intent.putExtra("STARRED", tarefa.stared)

                context.startActivity(intent)
            }
            /*
            // Marcar como favorito
            starIcon.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    //tarefa.stared = !tarefa.stared
                    notifyItemChanged(position)
                }
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(tarefas[position])
    }

    override fun getItemCount(): Int = tarefas.size

    fun updateTarefas(newTarefas: List<Tarefa>) {
        tarefas.clear()
        tarefas.addAll(newTarefas)
        notifyItemRangeChanged(0, tarefas.size) // Notifica apenas o intervalo alterado
    }

    /*fun removeItem(position: Int) {
        tarefas.removeAt(position)
        notifyItemRemoved(position)
    }

    fun markAsFavorite(position: Int) {
        val tarefa = tarefas[position]
        tarefa.stared = !tarefa.stared
        notifyItemChanged(position)
    }*/
}
