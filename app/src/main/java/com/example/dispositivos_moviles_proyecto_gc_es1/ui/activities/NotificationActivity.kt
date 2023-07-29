package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(layoutInflater)
        //Se debe ubicar en un lugar donde se cree una sola vez
        //Just once
        //createNotificationChannel()
        binding.btnNotification.setOnClickListener {

            sendNotification()
        }
        setContentView(binding.root)
    }
    //Construccion externa del canal
    val CHANNEL:String ="Notificacion 1"

    //Implementar el pending y flag

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal principal"
            val descriptionText = "Canal de notificaciones de variedades"
            val importance = NotificationManager.IMPORTANCE_HIGH
            //(ID DEL CANAL,name,importance)
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
     fun sendNotification() {
         //Contruccion internar de la notificacion
        var notif= NotificationCompat.Builder(this,CHANNEL)
         notif.setContentTitle("Notificacion title")
        //Notificacion con texto
         notif.setContentText("Tienes una notificacion")
         notif.setSmallIcon(R.drawable.marvel_icon)
         notif.setPriority(NotificationCompat.PRIORITY_DEFAULT)
         //Texto mas largo
        //Texto mucho mas extenso
         notif.setStyle(NotificationCompat.BigTextStyle().
         bigText("Esta es una notificacion para recordar que estamos"))
        //Envia la notificacion
        with(NotificationManagerCompat.from(this)){
             notify(1,notif.build())
         }
    }

}