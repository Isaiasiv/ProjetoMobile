package com.example.projetomobile.ui.alerta

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.projetomobile.R

class EntradaAlerta {
    fun showDesfocadoAlertBox(context: Context, message: String, onConfirm: (Boolean, String) -> Unit) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_alert_caixa_senha)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Referências para os componentes do layout
        val textAlertMensagem: TextView = dialog.findViewById(R.id.textMensagemAtraso)
        val textViewTitulo: TextView = dialog.findViewById(R.id.textDesfocado)
        val imageViewMascote: ImageView = dialog.findViewById(R.id.imageViewMascote)
        val btnAbrir: Button = dialog.findViewById(R.id.buttonAbrirDesfocado)
        val btnFechar: Button = dialog.findViewById(R.id.buttonFecharDesfocado)

        // Referência do EditText onde o usuário irá digitar a senha
        val editText: EditText = dialog.findViewById(R.id.editTextTextPassword)

        textAlertMensagem.text = message
        textViewTitulo.text = "Digite sua senha"
        btnAbrir.text = "Confirmar"
        btnFechar.text = "Cancelar"

        // Alterar a imagem
        imageViewMascote.setImageResource(R.drawable.focaobservadora)

        btnAbrir.setOnClickListener {
            // Obter o texto da senha
            val senha = editText.text.toString()

            // Chama a função de callback passando o status e a senha digitada
            onConfirm(true, senha)
            dialog.dismiss()
        }

        btnFechar.setOnClickListener {
            // Chama a função de callback indicando que a ação foi cancelada
            onConfirm(false, "")
            dialog.dismiss()
        }

        dialog.show()
    }
}
