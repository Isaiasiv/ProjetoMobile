package com.example.projetomobile.domain

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notificacoes {

    //Notificação que mostra que a atividade está proximo de atrasar

    //A notificação passa um context e um nome de atividade
    fun showNotificationProximoAtraso(context: Context, nomeAtividade: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(context, "notificação proximo atraso")
            //mudar icone para um que se adeque a notificação
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Atividade próxima da entrega")
            .setContentText("Olá! A atividade $nomeAtividade está próxima de atrasar.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(6, notification)
        }
    }

    //notificação que avisa que a atividade está atrasada

    //A notificação passa um context e um nome de atividade
    fun showNotificationAtraso(context: Context, nomeAtividade: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(context, "notificação atraso")
            //mudar icone para um que se adeque a notificação
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Atividade atrasada")
            .setContentText("Olá! A atividade $nomeAtividade está atrasada :(")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(7, notification)
        }
    }

    //Sempre que criar uma nova notificação tem quer criar um novo channel
    fun criarCanaisDeNotificacao(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                "notificação proximo atraso",
                "Notificação de Proximidade",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifica quando a atividade está próxima de atrasar"
            }

            val channel2 = NotificationChannel(
                "notificação atraso",
                "Notificação de Atraso",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifica quando a atividade está atrasada"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }
}