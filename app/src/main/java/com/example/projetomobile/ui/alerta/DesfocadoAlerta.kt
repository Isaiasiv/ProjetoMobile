package com.example.projetomobile.ui.alerta

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.projetomobile.R

import org.w3c.dom.Text

class DesfocadoAlerta {

    fun showDesfocadoAlertBox(context: Context, message:String){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_alert_desfocado)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textAlertMensagem: TextView = dialog.findViewById(R.id.textMensagemAtraso)
        val btnAbrir: Button = dialog.findViewById(R.id.buttonAbrirDesfocado)
        val btnFechar:Button = dialog.findViewById(R.id.buttonFecharDesfocado)

        textAlertMensagem.text = message

        btnAbrir.setOnClickListener{
            //escrever o codigo para abrir a atividade
        }

        btnFechar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}