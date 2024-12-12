package com.example.projetomobile.domain

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Verificacoes {

    private val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val notificacoes = Notificacoes()


    fun verificarData(data: String, context: Context, nomeAtividade: String) {

        notificacoes.criarCanaisDeNotificacao(context)

        val dataAlvoFormatado = try {
            formatoData.parse(data)
        } catch (e: Exception) {
            e.printStackTrace()
            return // Sai se a data estiver inválida
        }

        val calendarioDataAlvo = Calendar.getInstance()
        calendarioDataAlvo.time = dataAlvoFormatado

        val dataAtual = Calendar.getInstance()
        val diferenca = calendarioDataAlvo.timeInMillis - dataAtual.timeInMillis
        val diasRestantes = diferenca / (1000 * 60 * 60 * 24)

        if (diasRestantes in 0..3) {
            notificacoes.showNotificationProximoAtraso(context, nomeAtividade)
        } else if (diasRestantes > 3) {
            notificacoes.showNotificationAtraso(context, nomeAtividade)
        }
    }


}