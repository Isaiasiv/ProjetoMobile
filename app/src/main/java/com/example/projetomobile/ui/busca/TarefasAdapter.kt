import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomobile.databinding.ItemTarefaBinding

class TarefasAdapter : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {

    val tarefas = mutableListOf<Map<String, Any>>()

    class TarefaViewHolder(private val binding: ItemTarefaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarefa: Map<String, Any>) {
            binding.textViewTitulo.text = tarefa["titulo"] as? String ?: "Sem título"
            binding.textViewObjetivo.text = tarefa["objetivo"] as? String ?: "Sem objetivo"
            binding.textViewStatus.text = tarefa["descricao"] as? String ?: "Sem status"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val binding =
            ItemTarefaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(tarefas[position])
    }

    override fun getItemCount(): Int = tarefas.size

    fun updateTarefas(newTarefas: List<Map<String, Any>>) {
        tarefas.clear()
        tarefas.addAll(newTarefas)
        notifyDataSetChanged()
    }
}
