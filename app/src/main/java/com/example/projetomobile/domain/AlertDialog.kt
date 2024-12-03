package com.example.projetomobile.domain

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projetomobile.R
//TERMINAR ESSE CODIGO ADICIONANDO AS REGRAS DE NEGOCIO PARA ABRIR AUTOMATICAMENTE QUANDO UMA DATA DE ENTREGA
// ESTIVER ATRADASA.
class AlertDialog(private val context: Context) {

    fun showDialog(targetActivity: Class<out AppCompatActivity>){
        val dialog = Dialog(context)

        val view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_box,null)
        dialog.setContentView(view)

    val closeButton = view.findViewById<Button>(R.id.btnDialogCancel)
        closeButton.setOnClickListener{
            dialog.dismiss()
        }

        val navigateButton = view.findViewById<Button>(R.id.btnDialogViewAtividade)
        navigateButton.setOnClickListener{
            val intent = Intent(context,targetActivity)
            context.startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}