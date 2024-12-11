package com.example.projetomobile.ui.alerta

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.projetomobile.R

class ExcluirPerfilAlerta {

    fun showDesfocadoAlertBox(context: Context, message: String, onConfirm: (Boolean) -> Unit) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_alert_desfocado)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textAlertMensagem: TextView = dialog.findViewById(R.id.textMensagemAtraso)
        val textViewTitulo: TextView = dialog.findViewById(R.id.textDesfocado)
        val imageViewMascote: ImageView = dialog.findViewById(R.id.imageViewMascote)
        val btnAbrir: Button = dialog.findViewById(R.id.buttonAbrirDesfocado)
        val btnFechar: Button = dialog.findViewById(R.id.buttonFecharDesfocado)

        textAlertMensagem.text = message
        textViewTitulo.text = "Tem certeza em\n excluir sua conta?"
        btnAbrir.text = "Confirmar"
        btnFechar.text = "Cancelar"

        // Alterar a imagem
        imageViewMascote.setImageResource(R.drawable.focatriste)

        btnAbrir.setOnClickListener {
            onConfirm(true) // Retorna verdadeiro
            dialog.dismiss()
        }

        btnFechar.setOnClickListener {
            onConfirm(false) // Retorna falso
            dialog.dismiss()
        }

        dialog.show()
    }
}