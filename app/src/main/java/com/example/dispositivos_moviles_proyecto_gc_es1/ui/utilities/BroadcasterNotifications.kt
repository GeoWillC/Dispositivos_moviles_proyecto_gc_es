package com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities.NotificationActivity

class BroadcasterNotifications: BroadcastReceiver() {
    private val CHANNEL:String="Notificacion 1"

    override fun onReceive(context: Context, intent: Intent) {


        val myIntent=Intent(context, NotificationActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val notif = NotificationCompat.Builder(context, CHANNEL)
        notif.setContentTitle("Broadcaster notificacion mi llave :v")
        notif.setContentText("Abre tu wea :v")
        notif.setSmallIcon(R.drawable.marvel_icon)
        notif.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notif.setContentIntent(pendingIntent)
        notif.setAutoCancel(true)

        notif.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("alo")
        )

        //crear un manager de tipo broadcast
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,
            notif.build()
        )


    }

}