package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.dao.marvel.MarvelCharsDAO
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {

    abstract fun marvelDao() :MarvelCharsDAO

}