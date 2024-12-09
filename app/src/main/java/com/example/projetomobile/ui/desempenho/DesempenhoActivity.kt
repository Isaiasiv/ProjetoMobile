package com.example.projetomobile.ui.desempenho

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetomobile.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class DesempenhoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_desempenho)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Obtém a referência ao componente gráfico de pizza do layout.
        val graficoPizza: PieChart = findViewById(R.id.pizzaGrafico)

        // Cria uma lista de entradas para o gráfico de pizza, cada uma representando uma categoria com um valor
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(55f, "matematica"))// Adiciona "matemática" com 55% ao gráfico.
        entries.add(PieEntry(10f, "ciencias"))// Adiciona "ciências" com 10% ao gráfico.
        entries.add(PieEntry(20f, "geografia"))// Adiciona "geografia" com 20% ao gráfico.
        entries.add(PieEntry(15f, "banana")) // Adiciona "banana" com 15% ao gráfico

        // Cria um conjunto de dados para o gráfico de pizza usando as entradas.
        val pieDataSet: PieDataSet = PieDataSet(entries, "materias")

        // Define as cores para os segmentos do gráfico usando um template de cores pré-definido.
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS.toList())

        // Cria o objeto de dados do gráfico com o conjunto de dados.
        val pieData: PieData = PieData(pieDataSet)

        // Define os dados do gráfico no componente gráfico de pizza.
        graficoPizza.setData(pieData)

        // Desabilita a descrição do gráfico para uma aparência mais limpa.
        graficoPizza.description.isEnabled = false

        // Adiciona uma animação ao gráfico ao exibi-lo, animando a entrada dos dados no eixo Y.
        graficoPizza.animateY(1000)

        // Atualiza o gráfico para refletir as alterações.
        graficoPizza.invalidate()
    }
}