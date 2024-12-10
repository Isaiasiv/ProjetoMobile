package com.example.projetomobile.ui.tarefas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R

class TarefaAdapter(private val tarefas: MutableList<Email>) : RecyclerView.Adapter<TarefaAdapter.EmailViewHolder>() {

    inner class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userText: TextView = itemView.findViewById(R.id.txt_user)
        val subjectText: TextView = itemView.findViewById(R.id.txt_subject)
        val starIcon: ImageView = itemView.findViewById(R.id.img_star)

        fun bind(tarefa: Email) {
            userText.text = tarefa.user
            subjectText.text = tarefa.subject
            starIcon.setImageResource(if (tarefa.stared) R.drawable.ic_coracao_cheio else R.drawable.ic_coracao)

            // Clique no item para abrir nova atividade
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, TarefaActivity::class.java)

                // Passando dados para a nova atividade
                intent.putExtra("USER", tarefa.user)
                intent.putExtra("SUBJECT", tarefa.subject)
                intent.putExtra("STARRED", tarefa.stared)

                context.startActivity(intent)
            }

            // Marcar como favorito
            starIcon.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    tarefa.stared = !tarefa.stared
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(tarefas[position])
    }

    override fun getItemCount(): Int = tarefas.size

    fun removeItem(position: Int) {
        tarefas.removeAt(position)
        notifyItemRemoved(position)
    }

    fun markAsFavorite(position: Int) {
        val tarefa = tarefas[position]
        tarefa.stared = !tarefa.stared
        notifyItemChanged(position)
    }
}
