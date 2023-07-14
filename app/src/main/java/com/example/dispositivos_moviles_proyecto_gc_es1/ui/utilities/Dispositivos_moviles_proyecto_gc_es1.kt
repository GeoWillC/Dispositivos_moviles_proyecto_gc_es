package com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.MarvelConnectionDB
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.database.MarvelCharsDB

class Dispositivos_moviles_proyecto_gc_es1 : Application() {

    override fun onCreate() {
        super.onCreate()
        //Hace que cree una instancia con el contexto, la base de datos, y el nombre de la base
        db = Room.databaseBuilder(applicationContext, MarvelConnectionDB::class.java, "marvelDB")
            .build()

    }

    companion object {
        private var db: MarvelConnectionDB? = null

        fun getDbIntance(): MarvelConnectionDB {
            return db!!
        }
    }


}


