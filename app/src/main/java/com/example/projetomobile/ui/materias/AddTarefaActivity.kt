package com.example.projetomobile.ui.materias

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.example.projetomobile.data.TarefaRepository
import com.example.projetomobile.domain.CriarTarefaUseCase
import com.example.projetomobile.domain.Tarefa
import java.util.*

class AddTarefaActivity : AppCompatActivity() {

    private lateinit var criarTarefaUseCase: CriarTarefaUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_tarefa)

        // pega dados da tela e passa para o código
        val editTextNomeTarefa: EditText = findViewById(R.id.editTextNomeTarefa)
        val editTextTitulo: EditText = findViewById(R.id.editTextTitulo)
        val editTextObjetivo: EditText = findViewById(R.id.editTextObjetivo)
        val editTextDescricao: EditText = findViewById(R.id.editTextMultiLineDescricao)
        val textViewDataInicio: TextView = findViewById(R.id.textViewDataInicio)
        val textViewDataFim: TextView = findViewById(R.id.textViewDataFim)
        val buttonSalvar: Button = findViewById(R.id.button)

        // Inicializar repositório e caso de uso
        val tarefaRepository = TarefaRepository()
        criarTarefaUseCase = CriarTarefaUseCase(tarefaRepository)

        // Adiciona o listener para as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referências aos componentes da tela
        val buttonSelectDate: Button = findViewById(R.id.buttonSelectDateInicio)
        val textSelectedDate: TextView = findViewById(R.id.textViewDataInicio)

        // Referências aos novos componentes de Data Fim
        val buttonSelectDateFim: Button = findViewById(R.id.buttonSelectDateFim)
        val textSelectedDateFim: TextView = findViewById(R.id.textViewDataFim)

        // Configura o listener para o botão de seleção de data de início
        buttonSelectDate.setOnClickListener {
            // Obtém a data atual
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Cria o DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Formata e exibe a data selecionada no TextView
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    textSelectedDate.text = "Data Início: $formattedDate"
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        // Configura o listener para o botão de seleção de data de fim
        buttonSelectDateFim.setOnClickListener {
            // Obtém a data atual
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Cria o DatePickerDialog para a data de fim
            val datePickerDialogFim = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Formata e exibe a data selecionada no TextView
                    val formattedDateFim = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    textSelectedDateFim.text = "Data Final: $formattedDateFim"
                },
                year, month, day
            )
            datePickerDialogFim.show()
        }

        buttonSalvar.setOnClickListener {
            val nomeTarefa = editTextNomeTarefa.text.toString()
            val titulo = editTextTitulo.text.toString()
            val objetivo = editTextObjetivo.text.toString()
            val descricao = editTextDescricao.text.toString()
            val dataInicio = textViewDataInicio.text.toString()
            val dataFim = textViewDataFim.text.toString()

            if (nomeTarefa.isNotEmpty() && titulo.isNotEmpty() && dataInicio.isNotEmpty() && dataFim.isNotEmpty()) {
                val tarefa = Tarefa(
                    nome = nomeTarefa,
                    titulo = titulo,
                    objetivo = objetivo,
                    descricao = descricao,
                    dataInicio = dataInicio,
                    dataFim = dataFim
                )

                val materiaId = intent.getStringExtra("materia_id")

                // Verifique se o materiaId não é nulo
                if (materiaId.isNullOrEmpty()) {
                    // Mostra um erro caso não exista o ID da matéria
                    Toast.makeText(this, "Erro: Materia ID não encontrado.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Passando o ID da matéria para o uso no caso de criar a tarefa
                criarTarefaUseCase.execute(
                    materiaId = materiaId,  // O ID da matéria é passado aqui
                    tarefa = tarefa
                ) { sucesso, mensagem ->
                    if (sucesso) {
                        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao salvar tarefa: $mensagem", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
