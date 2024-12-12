package com.example.projetomobile.ui.materias
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.R
import com.example.projetomobile.domain.Materia
import com.example.projetomobile.ui.alerta.ExcluirAlerta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MateriaAdapter(
    private val materiasList: MutableList<Materia>,
    private val clickListener: (Materia) -> Unit,
    private val onDeleteSuccess: () -> Unit // Callback para notificar exclusão
) : RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materia, parent, false)
        return MateriaViewHolder(view, onDeleteSuccess, this)
    }

    override fun onBindViewHolder(holder: MateriaViewHolder, position: Int) {
        val materia = materiasList[position]
        holder.bind(materia, clickListener)
    }

    override fun getItemCount(): Int = materiasList.size

    fun removeMateria(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            materiasList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateMateria(position: Int, materia: Materia) {
        if (position != RecyclerView.NO_POSITION) {
            materiasList[position] = materia
            notifyItemChanged(position)
        }
    }

    class MateriaViewHolder(
        itemView: View,
        private val onDeleteSuccess: () -> Unit,
        private val adapter: MateriaAdapter
    ) : RecyclerView.ViewHolder(itemView) {
        private val textTitulo: TextView = itemView.findViewById(R.id.textViewTarefaTitulo)
        private val textDescricao: TextView = itemView.findViewById(R.id.textViewStatus)
        private val excluir: Button = itemView.findViewById(R.id.button_with_icon)
        private val usuarioId: String?
            get() = FirebaseAuth.getInstance().currentUser?.uid

        fun bind(materia: Materia, clickListener: (Materia) -> Unit) {
            textTitulo.text = materia.nome
            textDescricao.text = materia.descricao

            excluir.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_delete_forever_black_24dp, 0, 0)

            excluir.setOnClickListener {
                val alerta = ExcluirAlerta()
                alerta.showDesfocadoAlertBox(itemView.context, "A exclusão é permanente \n" +
                        "todas as tarefas serão excluídas") { confirmed ->
                    if (confirmed) {
                        usuarioId?.let { id ->
                            val db = FirebaseFirestore.getInstance()
                            db.collection("Usuarios").document(id)
                                .collection("materias").document(materia.id)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d("Delete", "Matéria deletada com sucesso!")
                                    Toast.makeText(itemView.context, "Matéria excluída com sucesso", Toast.LENGTH_SHORT).show()
                                    onDeleteSuccess()
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Delete", "Erro ao deletar a matéria", e)
                                }
                        }
                    }
                }
            }

            itemView.setOnClickListener {
                clickListener(materia)
            }

            itemView.setOnLongClickListener {
                showEditDialog(materia)
                true
            }
        }

        private fun showEditDialog(materia: Materia) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Editar Matéria")

            val input = EditText(itemView.context)
            input.setText(materia.nome)
            builder.setView(input)

            builder.setPositiveButton("Salvar") { dialog, _ ->
                val newName = input.text.toString()
                if (newName.isNotEmpty()) {
                    usuarioId?.let { id ->
                        val db = FirebaseFirestore.getInstance()
                        db.collection("Usuarios").document(id)
                            .collection("materias").document(materia.id)
                            .update("nome", newName)
                            .addOnSuccessListener {
                                Log.d("Update", "Nome da matéria atualizado com sucesso!")
                                Toast.makeText(itemView.context, "Nome da matéria atualizado com sucesso", Toast.LENGTH_SHORT).show()
                                materia.nome = newName
                                adapter.updateMateria(bindingAdapterPosition, materia)
                                dialog.dismiss()
                            }
                            .addOnFailureListener { e ->
                                Log.w("Update", "Erro ao atualizar o nome da matéria", e)
                                Toast.makeText(itemView.context, "Erro ao atualizar o nome da matéria", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }
}
