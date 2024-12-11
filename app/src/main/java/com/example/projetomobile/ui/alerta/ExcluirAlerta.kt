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

class ExcluirAlerta {
    fun showDesfocadoAlertBox(context: Context, message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_alert_desfocado)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textAlertMensagem: TextView = dialog.findViewById(R.id.textMensagemAtraso)
        val TextViewTitulo: TextView = dialog.findViewById(R.id.textDesfocado)
        val imageViewMascote: ImageView = dialog.findViewById(R.id.imageViewMascote) // Adiciona o ImageView
        val btnAbrir: Button = dialog.findViewById(R.id.buttonAbrirDesfocado)
        val btnFechar: Button = dialog.findViewById(R.id.buttonFecharDesfocado)

        textAlertMensagem.text = message
        TextViewTitulo.text = "Tem certeza?"
        btnAbrir.text = "Confirmar"
        btnFechar.text = "Cancelar"

        // Alterar a imagem
        imageViewMascote.setImageResource(R.drawable.focaobservadora)

        btnAbrir.setOnClickListener {
            // escrever o código para abrir a atividade
        }

        btnFechar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
