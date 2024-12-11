package com.example.projetomobile.ui.materias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Materia
import com.example.projetomobile.ui.alerta.ExcluirAlerta

class MateriaAdapter(
    private val materiasList: List<Materia>,
    private val clickListener: (Materia) -> Unit
) : RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materia, parent, false)
        return MateriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MateriaViewHolder, position: Int) {
        val materia = materiasList[position]
        holder.bind(materia, clickListener)
    }

    override fun getItemCount(): Int = materiasList.size

    class MateriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        private val textDescricao: TextView = itemView.findViewById(R.id.textViewStatus)
        private val excluir: Button = itemView.findViewById(R.id.button_with_icon)

        fun bind(materia: Materia, clickListener: (Materia) -> Unit) {
            textTitulo.text = materia.nome
            textDescricao.text = materia.descricao

            // Alterar o ícone do botão
            excluir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_delete_forever_black_24dp, 0, 0)

            excluir.setOnClickListener {
                val alerta = ExcluirAlerta()
                alerta.showDesfocadoAlertBox(itemView.context, "A exclusão e permanente") { confirmed ->
                    if (confirmed) {
                        // Usuário confirmou
                        clickListener(materia)
                    }
                }
            }

            itemView.setOnClickListener {
                clickListener(materia)  // Passando o objeto completo da matéria
            }
        }
    }
}
