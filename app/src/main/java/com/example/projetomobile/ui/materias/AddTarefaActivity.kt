package com.example.projetomobile.ui.materias

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import java.util.*

class AddTarefaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_tarefa)

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
                    textSelectedDate.text = "Data Início Selecionada: $formattedDate"
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
                    textSelectedDateFim.text = "Data Fim Selecionada: $formattedDateFim"
                },
                year, month, day
            )
            datePickerDialogFim.show()
        }
    }
}
